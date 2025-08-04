package com.example.historialacreditacionprueba.ui.uiScreen.screenDepartamento

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.Color
import com.example.historialacreditacionprueba.data.dataActividadesAlumnosApi.ActividadesAlumnosDto.AlumnoInscritoDto
import com.example.historialacreditacionprueba.data.dataActividadesAlumnosApi.InscritosViewModel
import com.example.historialacreditacionprueba.data.dataActividadesAlumnosApi.RetrofitClientAlumnoActividad
import com.example.historialacreditacionprueba.data.dataCarreras.UiState
import com.example.historialacreditacionprueba.data.dataLoginApi.AuthViewModel

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun InscritosScreen(
//    navController: NavController,
//    authViewModel: AuthViewModel,
//    actividadId: Int
//) {
//
//    val vm: InscritosViewModel = viewModel(
//        key = "inscritos_$actividadId"
//    ) { InscritosViewModel(authViewModel, RetrofitClientAlumnoActividad.api) } // Pasamos el api como argumento
//
//    val alumnos   by vm.alumnos.collectAsState()
//    val loading   by vm.loading.collectAsState()
//    val error     by vm.error.collectAsState()
//
//    LaunchedEffect(Unit) {
//        vm.cargarAlumnos(actividadId)
//    }
//
//    Scaffold(
//        topBar = { TopAppBar(title = { Text("Alumnos Inscritos") },
//            navigationIcon = {
//                IconButton(onClick = { navController.popBackStack() }) {
//                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver atrás")
//                }
//            },
//            colors = TopAppBarDefaults.topAppBarColors(
//                containerColor = MaterialTheme.colorScheme.primaryContainer,
//                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
//            )
//        )
//        }
//    ) { padding ->
//        Box(Modifier.padding(padding).fillMaxSize()) {
//            when {
//                loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
//                error   != null -> Text(
//                    text = error!!,
//                    color = MaterialTheme.colorScheme.error,
//                    modifier = Modifier.align(Alignment.Center)
//                )
//                else -> LazyColumn(
//                    verticalArrangement = Arrangement.spacedBy(8.dp),
//                    contentPadding = PaddingValues(16.dp)
//                ) {
//                    items(alumnos) { alumno ->
//                        AlumnosRow(
//                            alumno = alumno,
//                            onAprobar = { vm.aprobar(actividadId,it) },
//                            onReprobar = { vm.reprobar(actividadId, it) },
//                            onEliminar = { vm.eliminar(actividadId, it.alumnoId) }
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun AlumnosRow(
//    alumno: AlumnoInscritoDto,
//    onAprobar: (AlumnoInscritoDto) -> Unit,
//    onReprobar: (AlumnoInscritoDto) -> Unit,
//    onEliminar: (AlumnoInscritoDto) -> Unit
//) {
//    Card(elevation = CardDefaults.cardElevation(2.dp), modifier = Modifier.fillMaxWidth()) {
//        Row(
//            Modifier
//                .padding(12.dp)
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column {
//                Text(alumno.nombreCompleto, style = MaterialTheme.typography.titleMedium)
//                Spacer(Modifier.height(4.dp))
//            }
//            Row {
//                IconButton(onClick = { onAprobar(alumno) }) {
//                    Icon(Icons.Default.Check, contentDescription = "Aprobar",
//                        tint = MaterialTheme.colorScheme.primary)
//                }
//                IconButton(onClick = { onReprobar(alumno) }) {
//                    Icon(Icons.Default.Close, contentDescription = "Reprobar",
//                        tint = MaterialTheme.colorScheme.error)
//                }
//                IconButton(onClick = { onEliminar(alumno) }) {
//                    Icon(Icons.Default.Delete, contentDescription = "Eliminar",
//                        tint = Color.Gray)
//                }
//            }
//        }
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InscritosScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    actividadId: Int
) {
    val vm: InscritosViewModel = viewModel(
        key = "inscritos_$actividadId"
    ) {
        InscritosViewModel(authViewModel, actividadId, RetrofitClientAlumnoActividad.api)
    }

    val alumnosState by vm.alumnosState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Alumnos Inscritos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (val state = alumnosState) {
                UiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                is UiState.Error -> {
                    Text(
                        text     = state.message,
                        color    = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is UiState.Success -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding      = PaddingValues(16.dp)
                    ) {
                        items(state.data) { alumno ->
                            AlumnosRow(
                                alumno     = alumno,
                                onAprobar  = { vm.aprobar(actividadId, alumno) },
                                onReprobar = { vm.reprobar(actividadId, alumno) },
                                onEliminar = { vm.eliminar(actividadId, alumno.alumnoId) }
                            )
                        }
                    }
                }
                UiState.Idle -> {
                }
            }
        }
    }
}

@Composable
fun AlumnosRow(
    alumno: AlumnoInscritoDto,
    onAprobar: (AlumnoInscritoDto) -> Unit,
    onReprobar: (AlumnoInscritoDto) -> Unit,
    onEliminar: (AlumnoInscritoDto) -> Unit
) {
    // Elegimos un color de fondo según el estado:
    val targetColor = when(alumno.estadoAlumnoActividad) {
        4 -> MaterialTheme.colorScheme.secondaryContainer   // verde suave
        5 -> MaterialTheme.colorScheme.errorContainer       // rojo suave
        else -> MaterialTheme.colorScheme.surface           // normal
    }
    val bgColor by animateColorAsState(targetColor)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(alumno.nombreCompleto, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text(
                    text = when(alumno.estadoAlumnoActividad) {
                        4 -> "Aprobado"
                        5 -> "Reprobado"
                        else -> "Pendiente"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = when(alumno.estadoAlumnoActividad) {
                        4 -> MaterialTheme.colorScheme.secondary
                        5 -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
            Row {
                IconButton(
                    onClick = { onAprobar(alumno) },
                    enabled = alumno.estadoAlumnoActividad != 4
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Aprobar",
                        tint = if (alumno.estadoAlumnoActividad == 4)
                            MaterialTheme.colorScheme.secondary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(
                    onClick = { onReprobar(alumno) },
                    enabled = alumno.estadoAlumnoActividad != 5
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Reprobar",
                        tint = if (alumno.estadoAlumnoActividad == 5)
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(onClick = { onEliminar(alumno) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}

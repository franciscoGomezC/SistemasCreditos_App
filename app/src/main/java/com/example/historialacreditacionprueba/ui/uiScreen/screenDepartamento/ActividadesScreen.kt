package com.example.historialacreditacionprueba.ui.uiScreen.screenDepartamento

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.historialacreditacionprueba.DepartamentosScreen
import com.example.historialacreditacionprueba.R
import com.example.historialacreditacionprueba.data.dataActividadesApi.Actividades.ActividadDto
import com.example.historialacreditacionprueba.data.dataActividadesApi.ActividadesViewModel
import com.example.historialacreditacionprueba.data.dataCarreras.UiState
import com.example.historialacreditacionprueba.data.dataLoginApi.AuthViewModel

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ActividadesScreen(
//    navController: NavController,
//    authViewModel: AuthViewModel
//) {
//    val actividadesVM = remember { ActividadesViewModel(authViewModel) }
//
//    val actividades by actividadesVM.actividades.collectAsState()
//    val loading      by actividadesVM.loading.collectAsState()
//    val error        by actividadesVM.error.collectAsState()
//
//    val tiposActividad = listOf("Todos", "Deportivo", "Cultural", "Tutorias", "MOOC")
//
//    // Filtros locales
//    var filtroNombre by remember { mutableStateOf("") }
//    var filtroTipo   by remember { mutableStateOf(0) } // 0 = todos
//
//    var expandTipo by remember { mutableStateOf(false) }
//
//    // Cargamos una sola vez al mostrar este Composable
//    LaunchedEffect(Unit) {
//        actividadesVM.cargarActividades()
//    }
//
//    Scaffold(
//        topBar = { TopAppBar(title = { Text("Actividades") },
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
//            }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .padding(padding)
//                .verticalScroll(rememberScrollState())
//                .padding(12.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            // Filtro por nombre
//            OutlinedTextField(
//                value = filtroNombre,
//                onValueChange = { filtroNombre = it },
//                label = { Text("Buscar por nombre") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            // Filtro por tipo de actividad con ExposedDropdownMenuBox
//            ExposedDropdownMenuBox(
//                expanded = expandTipo,
//                onExpandedChange = { expandTipo = it }
//            ) {
//                OutlinedTextField(
//                    value = tiposActividad[filtroTipo],
//                    onValueChange = {},
//                    readOnly = true,
//                    label = { Text("Tipo de actividad") },
//                    trailingIcon = {
//                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandTipo)
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .menuAnchor()
//                )
//
//                ExposedDropdownMenu(
//                    expanded = expandTipo,
//                    onDismissRequest = { expandTipo = false }
//                ) {
//                    tiposActividad.forEachIndexed { idx, tipo ->
//                        DropdownMenuItem(
//                            text = { Text(tipo) },
//                            onClick = {
//                                filtroTipo = idx
//                                expandTipo = false
//                            }
//                        )
//                    }
//                }
//            }
//
//            // Lista filtrada
//            val actividadesFiltradas = actividades.filter { act ->
//                val coincideNombre = act.nombre.contains(filtroNombre, ignoreCase = true)
//                val coincideTipo   = filtroTipo == 0 || act.tipoActividad == filtroTipo
//                coincideNombre && coincideTipo
//            }
//
//            when {
//                loading -> CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
//                error != null -> Text(
//                    text = error!!,
//                    color = MaterialTheme.colorScheme.error,
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                )
//                else -> {
//                    actividadesFiltradas.forEach { act ->
//                        ActividadCard(
//                            actividad    = act,
//                            onUpdate     = { navController.navigate("${DepartamentosScreen.EditarActividadesApi.name}/${act.id}") },
//                            onVerAlumnos = { navController.navigate("${DepartamentosScreen.InscritosApi.name}/${act.id}") }
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActividadesScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val actividadesVM = remember { ActividadesViewModel(authViewModel) }

    val uiState by actividadesVM.actividadesState.collectAsState()

    val tiposActividad = listOf("Todos", "Deportivo", "Cultural", "Tutorias", "MOOC")
    var filtroNombre by remember { mutableStateOf("") }
    var filtroTipo   by remember { mutableStateOf(0) }
    var expandTipo   by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Actividades") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = filtroNombre,
                onValueChange = { filtroNombre = it },
                label = { Text("Buscar por nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expandTipo,
                onExpandedChange = { expandTipo = it }
            ) {
                OutlinedTextField(
                    value = tiposActividad[filtroTipo],
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de actividad") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandTipo)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandTipo,
                    onDismissRequest = { expandTipo = false }
                ) {
                    tiposActividad.forEachIndexed { idx, tipo ->
                        DropdownMenuItem(
                            text = { Text(tipo) },
                            onClick = {
                                filtroTipo = idx
                                expandTipo = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            when (val state = uiState) {
                UiState.Idle, UiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                }
                is UiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is UiState.Success -> {
                    val filtradas = state.data.filter { act ->
                        act.nombre.contains(filtroNombre, ignoreCase = true) &&
                                (filtroTipo == 0 || act.tipoActividad == filtroTipo)
                    }

                    if (filtradas.isEmpty()) {
                        Text(
                            "No hay actividades",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else {
                        filtradas.forEach { act ->
                            ActividadCard(
                                actividad    = act,
                                onUpdate     = {
                                    navController.navigate("${DepartamentosScreen.EditarActividadesApi.name}/${act.id}")
                                },
                                onVerAlumnos = {
                                    navController.navigate("${DepartamentosScreen.InscritosApi.name}/${act.id}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ActividadCard(
    actividad: ActividadDto,
    onUpdate: () -> Unit,
    onVerAlumnos: () -> Unit
) {
    val imageRes = when (actividad.tipoActividad) {
        1, 2 -> R.drawable.img5e
        3, 4 -> R.drawable.imagen2
        else -> R.drawable.cursos4
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = actividad.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Text(
                text = actividad.nombre,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = actividad.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Star, contentDescription = "Créditos", tint = MaterialTheme.colorScheme.secondary)
                    Text("Créditos: ${actividad.creditos}", style = MaterialTheme.typography.bodySmall)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Group, contentDescription = "Capacidad", tint = MaterialTheme.colorScheme.secondary)
                    Text("Capacidad: ${actividad.capacidad}", style = MaterialTheme.typography.bodySmall)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.DateRange, contentDescription = "Fechas", tint = MaterialTheme.colorScheme.secondary)
                    Text(
                        "Del ${actividad.fechaInicioIso.take(10)} al ${actividad.fechaFinIso.take(10)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onUpdate,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Actualizar")
                    Spacer(Modifier.width(6.dp))
                    Text("Actualizar")
                }

                OutlinedButton(
                    onClick = onVerAlumnos,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Visibility, contentDescription = "Ver Alumnos")
                    Spacer(Modifier.width(6.dp))
                    Text("Ver Alumnos")
                }
            }
        }
    }
}

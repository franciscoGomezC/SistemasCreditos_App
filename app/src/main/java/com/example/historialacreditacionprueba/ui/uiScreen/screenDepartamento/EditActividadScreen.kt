package com.example.historialacreditacionprueba.ui.uiScreen.screenDepartamento

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.historialacreditacionprueba.data.dataActividadesApi.ActividadUpdateViewModel
import com.example.historialacreditacionprueba.data.dataCarreras.UiState
import com.example.historialacreditacionprueba.data.dataLoginApi.AuthViewModel

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun EditActividadScreen(
//    navController: NavController,
//    authViewModel: AuthViewModel,
//    actividadId: Int
//){
//
//    val scrollState = rememberScrollState()
//    //se crea el viewmodel con remember
//    val vm = remember { ActividadUpdateViewModel(authViewModel, actividadId) }
//    val carreras by vm.carrerasDisponibles.collectAsState()
//    val carrerasSeleccionadas by vm.carrerasSeleccionadas.collectAsState()
//
//    // recoge sus estados
//    val original by vm.original.collectAsState()
//    val loading  by vm.loading.collectAsState()
//    val error    by vm.error.collectAsState()
//    val success  by vm.success.collectAsState()
//
//    // campos
//    val desc      by vm.descripcion.collectAsState()
//    val inicio    by vm.fechaInicioIso.collectAsState()
//    val fin       by vm.fechaFinIso.collectAsState()
//    val creds     by vm.creditos.collectAsState()
//    val cap       by vm.capacidad.collectAsState()
//    val estado    by vm.estadoActividad.collectAsState()
//
//    val estadoLabels = listOf("Activo", "En Progreso", "Finalizado")
//    var expandEstado by remember { mutableStateOf(false) }
//
//
//    LaunchedEffect(success) {
//        if (success) {
//            navController.popBackStack()
//        }
//    }
//
//    Scaffold(topBar = { TopAppBar(title = { Text("Editar actividad") },
//        navigationIcon = {
//            IconButton(onClick = { navController.popBackStack() }) {
//                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver atrás")
//            }
//        },
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
//        )) }) { padding ->
//        Column(
//            modifier = Modifier
//                .padding(padding)
//                .fillMaxSize()
//                .verticalScroll(scrollState)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            if (loading) {
//                CircularProgressIndicator()
//                Spacer(Modifier.height(16.dp))
//            }
//            if (original == null) return@Column
//
//            OutlinedTextField(value = desc, onValueChange = { vm.descripcion.value = it }, label = { Text("Descripción") })
//            OutlinedTextField(value = inicio, onValueChange = {vm.fechaInicioIso.value = it} , label = { Text("Fecha Inicio ISO") })
//            OutlinedTextField(value = fin, onValueChange = { vm.fechaFinIso.value = it }, label = { Text("Fecha Fin ISO") })
//            OutlinedTextField(value = creds, onValueChange = { vm.creditos.value = it }, label = { Text("Créditos") })
//            OutlinedTextField(value = cap, onValueChange = { vm.capacidad.value = it }, label = { Text("Capacidad") })
//
//            Box {
//                Text(
//                    text = estadoLabels.getOrNull(estado - 1) ?: "Seleccionar estado",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { expandEstado = true }
//                        .padding(12.dp)
//                        .border(
//                            1.dp,
//                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
//                            RoundedCornerShape(4.dp)
//                        ),
//                    color = if (estado in 1..estadoLabels.size)
//                        MaterialTheme.colorScheme.onSurface
//                    else
//                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
//                )
//
//                DropdownMenu(
//                    expanded = expandEstado,
//                    onDismissRequest = { expandEstado = false }
//                ) {
//                    estadoLabels.forEachIndexed { idx, label ->
//                        DropdownMenuItem(
//                            text = { Text(label) },
//                            onClick = {
//                                vm.estadoActividad.value = idx + 1
//                                expandEstado = false
//                            }
//                        )
//                    }
//                }
//            }
//
//            Text("Todas las carreras: ")
//            carreras.forEach { carrera ->
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { vm.toggleCarrera(carrera.id) }
//                        .padding(vertical = 4.dp)
//                ) {
//                    Checkbox(
//                        checked = carrerasSeleccionadas.contains(carrera.id),
//                        onCheckedChange = { vm.toggleCarrera(carrera.id) }
//                    )
//                    Spacer(Modifier.width(8.dp))
//                    Text(carrera.nombre)
//                }
//            }
//
//            Spacer(Modifier.height(16.dp))
//            Button(onClick = vm::updateActividad, Modifier.fillMaxWidth()) { Text("Guardar") }
//            if (error != null) Text(error!!, color = MaterialTheme.colorScheme.error)
//        }
//
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditActividadScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    actividadId: Int
) {
    val vm = remember { ActividadUpdateViewModel(authViewModel, actividadId) }
    val scrollState = rememberScrollState()

    val carreras             by vm.carrerasDisponibles.collectAsState()
    val seleccionadas        by vm.carrerasSeleccionadas.collectAsState()
    val desc                 by vm.descripcion.collectAsState()
    val inicioIso            by vm.fechaInicioIso.collectAsState()
    val finIso               by vm.fechaFinIso.collectAsState()
    val creds                by vm.creditos.collectAsState()
    val cap                  by vm.capacidad.collectAsState()
    val estado               by vm.estadoActividad.collectAsState()

    val estadoLabels = listOf("Activo", "En Progreso", "Finalizado")
    var expandEstado by remember { mutableStateOf(false) }

    val updateState by vm.updateState.collectAsState()
    LaunchedEffect(updateState) {
        if (updateState is UiState.Success) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Actividad") },
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
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = desc,
                onValueChange = vm::onDescripcionChange,
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = inicioIso,
                onValueChange =  vm::onFechaInicioIsoChange,
                label = { Text("Fecha Inicio (ISO)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = finIso,
                onValueChange = vm::onFechaFinIsoChange,
                label = { Text("Fecha Fin (ISO)") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = creds,
                    onValueChange =  vm::onCreditosChange,
                    label = { Text("Créditos") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = cap,
                    onValueChange = vm::onCapacidadChange,
                    label = { Text("Capacidad") },
                    modifier = Modifier.weight(1f)
                )
            }

            Box {
                Text(
                    text = estadoLabels.getOrNull(estado - 1) ?: "Seleccionar estado",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandEstado = true }
                        .padding(12.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            RoundedCornerShape(4.dp)
                        ),
                    color = if (estado in 1..estadoLabels.size)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
                DropdownMenu(
                    expanded = expandEstado,
                    onDismissRequest = { expandEstado = false }
                ) {
                    estadoLabels.forEachIndexed { idx, label ->
                        DropdownMenuItem(
                            text = { Text(label) },
                            onClick = {
                                vm.onEstadoActividadChange(idx + 1)
                                expandEstado = false
                            }
                        )
                    }
                }
            }

            Text("Carreras")
            carreras.forEach { carrera ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { vm.toggleCarrera(carrera.id) }
                        .padding(vertical = 4.dp)
                ) {
                    Checkbox(
                        checked = carrera.id in seleccionadas,
                        onCheckedChange = { vm.toggleCarrera(carrera.id) }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(carrera.nombre)
                }
            }

            Spacer(Modifier.height(16.dp))

            when (updateState) {
                UiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                }
                is UiState.Error -> {
                    Text(
                        text = (updateState as UiState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { vm.updateActividad() }, Modifier.fillMaxWidth()) {
                        Text("Reintentar")
                    }
                }
                else -> {
                    Button(
                        onClick = { vm.updateActividad() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar cambios")
                    }
                }
            }
        }
    }
}

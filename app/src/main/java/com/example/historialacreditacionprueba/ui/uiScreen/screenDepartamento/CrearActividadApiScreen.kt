package com.example.historialacreditacionprueba.ui.uiScreen.screenDepartamento

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.historialacreditacionprueba.data.dataActividadesApi.ActividadCreateViewModel
import com.example.historialacreditacionprueba.data.dataCarreras.Carrera
import com.example.historialacreditacionprueba.data.dataCarreras.UiState
import com.example.historialacreditacionprueba.data.dataLoginApi.AuthViewModel
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CrearActividadApiScreen(
//    navController: NavController,
//    authViewModel: AuthViewModel = viewModel()
//) {
//    val actividadVM = remember {
//        ActividadCreateViewModel(authViewModel)
//    }
//
//    // Coordinar estados de UI
//    val nombre        by actividadVM.nombre.collectAsState()
//    val descripcion   by actividadVM.descripcion.collectAsState()
//    val fechaInicio   by actividadVM.fechaInicio.collectAsState()
//    val fechaFin      by actividadVM.fechaFin.collectAsState()
//    val creditos      by actividadVM.creditos.collectAsState()
//    val capacidad     by actividadVM.capacidad.collectAsState()
//    val dias          by actividadVM.dias.collectAsState()
//    val horaInicio    by actividadVM.horaInicio.collectAsState()
//    val horaFin       by actividadVM.horaFin.collectAsState()
//    val tipoAct       by actividadVM.tipoActividad.collectAsState()
//    val estadoAct     by actividadVM.estadoActividad.collectAsState()
//    val carreras      by actividadVM.carreras.collectAsState()
//    val selectedIds   by actividadVM.carreraIds.collectAsState()
//    val loading       by actividadVM.loading.collectAsState()
//    val error         by actividadVM.error.collectAsState()
//    val success       by actividadVM.success.collectAsState()
//
//    // Labels para los enums de día
//    val diasLabels = listOf("Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo")
//    val tipoLabels = listOf("Deportiva", "Cultural", "Tutorías", "MOOC")
//    val estadoLabels = listOf("Activo", "En Progreso", "Finalizado")
//
//    var expandTipo by remember { mutableStateOf(false) }
//
//    val scrollState = rememberScrollState()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Cursos y Alumnos") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver atrás")
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            )
//        }
//    ) { paddingValues ->
//
//    Column(
//        modifier = Modifier
//            .verticalScroll(scrollState)
//            .padding(paddingValues)
//            .padding(16.dp)
//    ) {
//        Text("Crear Actividad", style = MaterialTheme.typography.titleLarge)
//        Spacer(Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = nombre,
//            onValueChange = { actividadVM.nombre.value = it },
//            label = { Text("Nombre") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = descripcion,
//            onValueChange = { actividadVM.descripcion.value = it },
//            label = { Text("Descripción") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = fechaInicio,
//            onValueChange = { actividadVM.fechaInicio.value = it },
//            label = { Text("Fecha Inicio (ISO)") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(8.dp))
//        OutlinedTextField(
//            value = fechaFin,
//            onValueChange = { actividadVM.fechaFin.value = it },
//            label = { Text("Fecha Fin (ISO)") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(8.dp))
//
//        Row(Modifier.fillMaxWidth()) {
//            OutlinedTextField(
//                value = creditos,
//                onValueChange = { actividadVM.creditos.value = it },
//                label = { Text("Créditos") },
//                modifier = Modifier.weight(1f)
//            )
//            Spacer(Modifier.width(8.dp))
//            OutlinedTextField(
//                value = capacidad,
//                onValueChange = { actividadVM.capacidad.value = it },
//                label = { Text("Capacidad") },
//                modifier = Modifier.weight(1f)
//            )
//        }
//        Spacer(Modifier.height(8.dp))
//
//        var expandDias by remember { mutableStateOf(false) }
//        Text("Día")
//        Box {
//            Text(
//                diasLabels.getOrNull(dias - 1) ?: "Seleccionar",
//                Modifier
//                    .clickable { expandDias = true }
//                    .padding(8.dp)
//            )
//            DropdownMenu(expanded = expandDias, onDismissRequest = { expandDias = false }) {
//                diasLabels.forEachIndexed { idx, label ->
//                    DropdownMenuItem(text = { Text(label) }, onClick = {
//                        actividadVM.dias.value = idx + 1
//                        expandDias = false
//                    })
//                }
//            }
//        }
//        Spacer(Modifier.height(8.dp))
//
//        Row(Modifier.fillMaxWidth()) {
//            OutlinedTextField(
//                value = horaInicio,
//                onValueChange = { actividadVM.horaInicio.value = it },
//                label = { Text("Hora Inicio (HH:mm)") },
//                modifier = Modifier.weight(1f)
//            )
//            Spacer(Modifier.width(8.dp))
//            OutlinedTextField(
//                value = horaFin,
//                onValueChange = { actividadVM.horaFin.value = it },
//                label = { Text("Hora Fin (HH:mm)") },
//                modifier = Modifier.weight(1f)
//            )
//        }
//        Spacer(Modifier.height(8.dp))
//
//        // Tipo y Estado
//        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//            Box(modifier = Modifier.weight(1f)) {
//                Text(
//                    text = tipoLabels.getOrNull(tipoAct - 1) ?: "Seleccionar tipo",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { expandTipo = true }
//                        .padding(12.dp)
//                        .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), RoundedCornerShape(4.dp)),
//                    color = if (tipoAct in 1..tipoLabels.size) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
//                )
//
//                DropdownMenu(
//                    expanded = expandTipo,
//                    onDismissRequest = { expandTipo = false }
//                ) {
//                    tipoLabels.forEachIndexed { idx, label ->
//                        DropdownMenuItem(
//                            text = { Text(label) },
//                            onClick = {
//                                actividadVM.tipoActividad.value = idx + 1
//                                expandTipo = false
//                            }
//                        )
//                    }
//                }
//            }
//
//            Spacer(Modifier.width(8.dp))
//            Box(modifier = Modifier.weight(1f)) {
//                Text(
//                    text = estadoLabels[0],
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(12.dp)
//                        .border(
//                            1.dp,
//                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
//                            RoundedCornerShape(4.dp)
//                        ),
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//            }
//        }
//
//        Spacer(Modifier.height(8.dp))
//
//        Text("Carreras")
//        carreras.forEach { c ->
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Checkbox(
//                    checked = selectedIds.contains(c.id),
//                    onCheckedChange = { actividadVM.toggleCarrera(c.id) }
//                )
//                Text(c.nombre)
//            }
//        }
//        Spacer(Modifier.height(16.dp))
//
//        // Mensajes de estado
//        if (error != null) Text(error!!, color = Color.Red)
//        if (success) Text("¡Actividad creada con éxito!", color = Color.Green)
//        Spacer(Modifier.height(16.dp))
//
//        // Botón de envío
//        Button(
//            onClick = { actividadVM.createActividad() },
//            enabled = !loading,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            if (loading) CircularProgressIndicator(Modifier.size(24.dp))
//            else Text("Crear Actividad")
//        }
//    }
//    }
//}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearActividadApiScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val vm = remember { ActividadCreateViewModel(authViewModel) }

    val nombre        by vm.nombre.collectAsState()
    val descripcion   by vm.descripcion.collectAsState()
    val fechaInicio   by vm.fechaInicio.collectAsState()
    val fechaFin      by vm.fechaFin.collectAsState()
    val creditos      by vm.creditos.collectAsState()
    val capacidad     by vm.capacidad.collectAsState()
    val dias          by vm.dias.collectAsState()
    val horaInicio    by vm.horaInicio.collectAsState()
    val horaFin       by vm.horaFin.collectAsState()
    val tipoAct       by vm.tipoActividad.collectAsState()
    val estadoAct     by vm.estadoActividad.collectAsState()
    val carreraIds    by vm.carreraIds.collectAsState()
    val genero        by vm.genero.collectAsState()

    val carrerasState by vm.carrerasState.collectAsState()
    val createState   by vm.createState.collectAsState()

    LaunchedEffect(createState) {
        if (createState is UiState.Success) {
            navController.popBackStack()
        }
    }

    val diasLabels   = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    val tipoLabels   = listOf("Deportiva", "Cultural", "Tutorías", "MOOC")
    val estadoLabels = listOf("Activo", "En Progreso", "Finalizado")
    val scrollState  = rememberScrollState()
    var expandTipo   by remember { mutableStateOf(false) }
    var expandDias   by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Actividad") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .verticalScroll(scrollState)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { vm.nombre.value = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { vm.descripcion.value = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fechaInicio,
                onValueChange = { vm.fechaInicio.value = it },
                label = { Text("Fecha Inicio (AAAA-MM-DDTHH:mm:ss)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fechaFin,
                onValueChange = { vm.fechaFin.value = it },
                label = { Text("Fecha Fin (AAAA-MM-DDTHH:mm:ss)") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = creditos,
                    onValueChange = { vm.creditos.value = it },
                    label = { Text("Créditos") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextField(
                    value = capacidad,
                    onValueChange = { vm.capacidad.value = it },
                    label = { Text("Capacidad") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Text("Día")
            Box {
                Text(
                    diasLabels.getOrNull(dias - 1) ?: "Seleccionar",
                    Modifier
                        .fillMaxWidth()
                        .clickable { expandDias = true }
                        .padding(12.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            RoundedCornerShape(4.dp)
                        )
                )
                DropdownMenu(expanded = expandDias, onDismissRequest = { expandDias = false }) {
                    diasLabels.forEachIndexed { idx, label ->
                        DropdownMenuItem(
                            text = { Text(label) },
                            onClick = {
                                vm.dias.value = idx + 1
                                expandDias = false
                            }
                        )
                    }
                }
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = horaInicio,
                    onValueChange = { vm.horaInicio.value = it },
                    label = { Text("Hora Inicio (HH:mm)") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = horaFin,
                    onValueChange = { vm.horaFin.value = it },
                    label = { Text("Hora Fin (HH:mm)") },
                    modifier = Modifier.weight(1f)
                )
            }

            // Tipo de Actividad
            Text("Tipo de Actividad")
            Box {
                Text(
                    tipoLabels.getOrNull(tipoAct - 1) ?: "Seleccionar tipo",
                    Modifier
                        .fillMaxWidth()
                        .clickable { expandTipo = true }
                        .padding(12.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            RoundedCornerShape(4.dp)
                        )
                )
                DropdownMenu(expanded = expandTipo, onDismissRequest = { expandTipo = false }) {
                    tipoLabels.forEachIndexed { idx, label ->
                        DropdownMenuItem(
                            text = { Text(label) },
                            onClick = {
                                vm.tipoActividad.value = idx + 1
                                expandTipo = false
                            }
                        )
                    }
                }
            }

            Text("Estado: ${estadoLabels.getOrNull(estadoAct - 1) ?: "?"}")

            Text("Carreras")
            when (carrerasState) {
                UiState.Loading -> CircularProgressIndicator()
                is UiState.Error -> Text((carrerasState as UiState.Error).message, color = Color.Red)
                is UiState.Success -> {
                    (carrerasState as UiState.Success<List<Carrera>>).data.forEach { c ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val checked = carreraIds.contains(c.id)
                            Checkbox(checked, onCheckedChange = { vm.toggleCarrera(c.id) })
                            Text(c.nombre)
                        }
                    }
                }
                else -> {}
            }

            val isCreating = createState is UiState.Loading
            Button(
                onClick = { vm.createActividad() },
                enabled = !isCreating,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isCreating) CircularProgressIndicator(modifier = Modifier.size(24.dp))
                else Text("Crear Actividad")
            }

            when (createState) {
                is UiState.Error -> Text((createState as UiState.Error).message, color = Color.Red)
                is UiState.Success -> Text("¡Actividad creada!", color = Color.Green)
                else -> {}
            }
        }
    }
}
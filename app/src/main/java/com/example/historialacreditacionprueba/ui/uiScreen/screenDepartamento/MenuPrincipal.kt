package com.example.historialacreditacionprueba.ui.uiScreen.screenDepartamento

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.historialacreditacionprueba.DepartamentosScreen
import com.example.historialacreditacionprueba.data.dataLoginApi.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.historialacreditacionprueba.data.dataCarreras.UiState
import com.example.historialacreditacionprueba.data.dataDepartamentoApi.DepartamentoDto

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MenuPrincipal(navController: NavController, viewModel: AuthViewModel = viewModel()) {
//
//    // 1) Recoge el flujo como estado de Compose
//    val loginResponse by viewModel.loginResponse.collectAsStateWithLifecycle()
//    val departamento by viewModel.departamento.collectAsState()
//    val loading by viewModel.loadingDepto.collectAsState()
//    val error by viewModel.errorDepto.collectAsState()
//
//    // 2) Usa esa variable local en tu LaunchedEffect
//    LaunchedEffect(key1 = loginResponse) {
//        if (loginResponse?.departamentoId != null) {
//            viewModel.fetchDepartamento()
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Menú", style = MaterialTheme.typography.titleLarge) },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            )
//        }
//    ) { paddingValues ->
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(24.dp)
//                .background(MaterialTheme.colorScheme.surfaceContainer),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            if (loading) {
//                CircularProgressIndicator()
//            } else if (error != null) {
//                Text(error!!, color = Color.Red)
//            } else {
//                Text(
//                    text = "¡Bienvenido, ${departamento?.nombre ?: "Departamento"}!",
//                    style = MaterialTheme.typography.headlineMedium,
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
//
//            Spacer(Modifier.height(50.dp))
//
//            Button(
//                onClick = { navController.navigate(DepartamentosScreen.CrearActividadApi.name) },
//                modifier = Modifier.fillMaxWidth().height(60.dp)
//            ) {
//                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = MaterialTheme.colorScheme.onPrimary)
//                Spacer(Modifier.width(8.dp))
//                Text("Agregar Actividad", style = MaterialTheme.typography.bodyLarge)
//            }
//
//            Spacer(Modifier.height(24.dp))
//
//            Button(
//                onClick = { navController.navigate(DepartamentosScreen.ActividadesApi.name) },
//                modifier = Modifier.fillMaxWidth().height(60.dp)
//            ) {
//                Icon(Icons.Default.List, contentDescription = "Actividades", tint = MaterialTheme.colorScheme.onPrimary)
//                Spacer(Modifier.width(8.dp))
//                Text("Actividades", style = MaterialTheme.typography.bodyLarge)
//            }
//
////            Spacer(Modifier.height(24.dp))
////            Button(onClick = { navController.navigate(DepartamentosScreen.Comentarios.name) },
////                modifier = Modifier.fillMaxWidth().height(60.dp)) {
////                Text("Comentarios", style = MaterialTheme.typography.bodyLarge)
////            }
//
//            Spacer(Modifier.height(40.dp))
//            Button(onClick = { navController.navigate(DepartamentosScreen.Carreras.name) },
//                modifier = Modifier.fillMaxWidth().height(60.dp)) {
//                Text("Carreras", style = MaterialTheme.typography.bodyLarge)
//            }
//
//            Spacer(Modifier.height(40.dp))
//
////            Button(
////                onClick = {
////                    viewModel.cerrarSesion()
////                    navController.navigate(DepartamentosScreen.LoginApi.name) {
////                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
////                    }
////                },
////                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
////                modifier = Modifier.fillMaxWidth().height(60.dp)
////            ) {
////                Text("Cerrar Sesión", style = MaterialTheme.typography.bodyLarge, color = Color.White)
////            }
//
//            Button(
//                onClick = {
//                    viewModel.cerrarSesion()
//                    navController.navigate(DepartamentosScreen.LoginApi.name) {
//                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
//                    }
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
//                modifier = Modifier.fillMaxWidth().height(60.dp)
//            ) {
//                Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión", tint = Color.White)
//                Spacer(Modifier.width(8.dp))
//                Text("Cerrar Sesión", style = MaterialTheme.typography.bodyLarge, color = Color.White)
//            }
//
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipal(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    // 1) Suscríbete al estado del login y del departamento
    val loginUi by viewModel.loginState.collectAsState()
    val deptoUi by viewModel.departamentoState.collectAsState()

    // 2) Si el login no ha ido bien, redirige a la pantalla de login
    LaunchedEffect(loginUi) {
        if (loginUi is UiState.Error) {
            navController.navigate(DepartamentosScreen.LoginApi.name) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menú", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .background(MaterialTheme.colorScheme.surfaceContainer),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (deptoUi) {
                UiState.Idle -> { /* Esperando carga */ }
                UiState.Loading -> CircularProgressIndicator()
                is UiState.Error -> Text(text = (deptoUi as UiState.Error).message, color = Color.Red)
                is UiState.Success -> {
                    val nombreDep = (deptoUi as UiState.Success<DepartamentoDto>).data.nombre
                    Text(
                        text = "¡Bienvenido, $nombreDep!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.height(50.dp))

            // 4) Botones de navegación de tu menú
            Button(
                onClick = { navController.navigate(DepartamentosScreen.CrearActividadApi.name) },
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = MaterialTheme.colorScheme.onPrimary)
                Spacer(Modifier.width(8.dp))
                Text("Agregar Actividad", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(DepartamentosScreen.ActividadesApi.name) },
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Icon(Icons.Default.List, contentDescription = "Actividades", tint = MaterialTheme.colorScheme.onPrimary)
                Spacer(Modifier.width(8.dp))
                Text("Actividades", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate(DepartamentosScreen.Carreras.name) },
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Text("Carreras", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(Modifier.height(40.dp))

            // 5) Cerrar sesión vuelve a estado inicial y navega al login
            Button(
                onClick = {
                    viewModel.cerrarSesion()
                    navController.navigate(DepartamentosScreen.LoginApi.name) {
                        popUpTo(DepartamentosScreen.MenuPrincipal.name) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión", tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Cerrar Sesión", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            }
        }
    }
}

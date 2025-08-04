package com.example.historialacreditacionprueba.ui.uiScreen.ScreenApiCarrera

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.historialacreditacionprueba.data.dataCarreras.Carrera
import com.example.historialacreditacionprueba.data.dataCarreras.CarrerasViewModel
import com.example.historialacreditacionprueba.data.dataCarreras.UiState

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CarrerasScreen(
//    navController: NavController,
//    viewModel: CarrerasViewModel = viewModel()
//) {
//    val carreras by viewModel.carreras.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.cargarCarreras()
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("Lista de Carreras") },
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
//    ) { padding ->
//        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
//            items(carreras) { carrera ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                    elevation = CardDefaults.cardElevation(4.dp)
//                ) {
//                    Text(
//                        text = carrera.nombre,
//                        modifier = Modifier.padding(16.dp),
//                        style = MaterialTheme.typography.titleMedium
//                    )
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarrerasScreen(
    navController: NavController,
    viewModel: CarrerasViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Carreras") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        when (val s = state) {
            UiState.Idle -> {}
            UiState.Loading -> LoadingView(padding)
            is UiState.Error -> ErrorView(s.message, padding)
            is UiState.Success -> CarrerasListView(s.data, padding)
        }
    }
}

@Composable
fun LoadingView(padding: PaddingValues) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(padding)) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
fun ErrorView(message: String, padding: PaddingValues) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(padding)) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun CarrerasListView(lista: List<Carrera>, padding: PaddingValues) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .padding(16.dp)) {
        items(lista) { carrera ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    text = carrera.nombre,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

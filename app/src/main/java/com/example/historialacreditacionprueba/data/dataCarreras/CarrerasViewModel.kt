package com.example.historialacreditacionprueba.data.dataCarreras

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//class CarrerasViewModel: ViewModel() {
//    private val _carreras = MutableStateFlow<List<Carrera>>(emptyList())
//    val carreras: StateFlow<List<Carrera>> = _carreras
//
//    fun cargarCarreras() {
//        viewModelScope.launch {
//            try {
//                val carreras = RetrofitClientCarrera.carreraApiService.obtenerCarreras()
//                _carreras.value = carreras
//            } catch (e: Exception) {
//                println("Error: ${e.message}")
//            }
//        }
//    }
//}

class CarrerasViewModel: ViewModel() {

    private val api: CarreraApiService = RetrofitClientCarrera.carreraApiService

    val uiState: StateFlow<UiState<List<Carrera>>> = flow {
        emit(UiState.Loading)
        val lista = api.obtenerCarreras()
        emit(UiState.Success(lista))
    }
        .retry(2)
        .catch { e -> emit(UiState.Error(e.message ?: "Error desconocido")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Idle
        )
}
package com.example.historialacreditacionprueba.data.dataActividadesApi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.historialacreditacionprueba.data.dataActividadesApi.Actividades.ActividadDto
import com.example.historialacreditacionprueba.data.dataCarreras.UiState
import com.example.historialacreditacionprueba.data.dataLoginApi.AuthViewModel
import com.example.historialacreditacionprueba.data.dataLoginApi.LoginResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//class ActividadesViewModel(
//    private val authViewModel: AuthViewModel
//) : ViewModel() {
//
//    private val _actividades = MutableStateFlow<List<ActividadDto>>(emptyList())
//    val actividades: StateFlow<List<ActividadDto>> = _actividades
//
//    private val _loading = MutableStateFlow(false)
//    val loading: StateFlow<Boolean> = _loading
//
//    private val _error = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?> = _error
//
//    fun cargarActividades() {
//        viewModelScope.launch {
//            _loading.value = true
//            _error.value = null
//            try {
//                //loginStateFuncional
//                //validamos que el loginState esté en Success.
//                val loginUiState = authViewModel.loginState.value // UIState.Success<LoginResponseDto>
//
//                // Validamos que el loginState esté en Success.
//                if (loginUiState !is UiState.Success) {
//                    // Si no es Success, podría ser Loading, Error, o algún otro estado inicial.
//                    // Podrías querer manejar UiState.Error específicamente si tiene un mensaje.
//                    if (loginUiState is UiState.Error) {
//                        _error.value = "Error de autenticación: ${loginUiState.message}"
//                    } else {
//                        _error.value = "Error de autenticación: Sesión no iniciada o estado inesperado."
//                    }
//                    _loading.value = false // Detener el indicador de carga
//                    return@launch // Retorna solo de esta corrutina (lambda del launch)
//                }
//
//                // En este punto, loginUiState ES UiState.Success gracias al smart cast
//                // y el return@launch anterior.
//                // La propiedad 'data' de UiState.Success<LoginResponseDto> contiene LoginResponseDto.
//                val tokenFromLogin = loginUiState.data.token
//                val fullToken = "Bearer $tokenFromLogin"
//
//                val lista = RetrofitClientActividad.actividadApiService
//                    .obtenerActividades(fullToken)
//                _actividades.value = lista
//            } catch (e: Exception) {
//                _error.value = "No se pudo cargar actividades"
//            } finally {
//                _loading.value = falsez
//            }
//        }
//    }
//}

class ActividadesViewModel(
    authViewModel: AuthViewModel
) : ViewModel() {

    private val api = RetrofitClientActividad.actividadApiService


    private val tokenFlow: Flow<String> = authViewModel.loginState
        .filterIsInstance<UiState.Success<LoginResponseDto>>()
        .map { success -> "Bearer ${success.data.token}" }

    val actividadesState: StateFlow<UiState<List<ActividadDto>>> = tokenFlow
        .flatMapLatest { bearer ->
            flow {
                emit(UiState.Loading)
                val lista = api.obtenerActividades(bearer)
                emit(UiState.Success(lista))
            }
                .catch { e -> emit(UiState.Error(e.message ?: "Error desconocido")) }

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Idle
        )
}

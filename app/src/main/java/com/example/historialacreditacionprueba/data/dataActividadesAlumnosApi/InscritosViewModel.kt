package com.example.historialacreditacionprueba.data.dataActividadesAlumnosApi

import androidx.compose.material3.Text
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.historialacreditacionprueba.data.dataActividadesAlumnosApi.ActividadesAlumnosDto.AlumnoActividadUpdateDto
import com.example.historialacreditacionprueba.data.dataActividadesAlumnosApi.ActividadesAlumnosDto.AlumnoInscritoDto
import com.example.historialacreditacionprueba.data.dataCarreras.UiState
import com.example.historialacreditacionprueba.data.dataLoginApi.AuthViewModel
import com.example.historialacreditacionprueba.data.dataLoginApi.LoginResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

//class InscritosViewModel(
//    private val authViewModel: AuthViewModel,
//    private val api: AlumnoActividadApiService
//) : ViewModel() {
//
//    private val _alumnos = MutableStateFlow<List<AlumnoInscritoDto>>(emptyList())
//    val alumnos: StateFlow<List<AlumnoInscritoDto>> = _alumnos
//
//    private val _loading = MutableStateFlow(false)
//    val loading: StateFlow<Boolean> = _loading
//
//    private val _error = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?> = _error
//
//
//    fun cargarAlumnos(actividadId: Int) {
//        viewModelScope.launch {
//            _loading.value = true
//            _error.value = null
//            try {
//
//                // 1) Validar estado de login
//                val loginUi = authViewModel.loginState.value
//                if (loginUi !is UiState.Success) {
//                    _error.value = when (loginUi) {
//                        is UiState.Error   -> "Auth error: ${loginUi.message}"
//                        else               -> "Debes iniciar sesión primero"
//                    }
//                    return@launch
//                }
//
//                // 2) Construir header
//                val header = "Bearer ${loginUi.data.token}"
//
//                // 3) Llamada
//                _alumnos.value = api.obtenerInscritos(header, actividadId)
//            } catch (e: Exception) {
//                _error.value = "No se pudo cargar alumnos"
//            } finally {
//                _loading.value = false
//            }
//        }
//    }
//
//    fun aprobar(actividadId: Int, alumno: AlumnoInscritoDto) {
//        actualizarEstado(
//            actividadId = actividadId,
//            alumnoId = alumno.alumnoId,
//            nuevoEstado = 4, // acreditado
//            fechaInscripcion = alumno.fechaInscripcion,
//            genero = alumno.genero
//        )
//    }
//
//    fun reprobar(actividadId: Int, alumno: AlumnoInscritoDto) {
//        actualizarEstado(
//            actividadId = actividadId,
//            alumnoId = alumno.alumnoId,
//            nuevoEstado = 5, // No acreditado
//            fechaInscripcion = alumno.fechaInscripcion,
//            genero = alumno.genero
//        )
//    }
//
//    private fun actualizarEstado(
//        actividadId: Int,
//        alumnoId: Int,
//        nuevoEstado: Int,
//        fechaInscripcion: String,
//        genero: Int
//    ) = viewModelScope.launch {
//        _loading.value = true
//        _error.value   = null
//
//        // Validar token
//        val loginUi = authViewModel.loginState.value
//        if (loginUi !is UiState.Success) {
//            _error.value = "Debes iniciar sesión"
//            _loading.value = false
//            return@launch
//        }
//        val header = "Bearer ${loginUi.data.token}"
//
//        // Llamada PUT
//        val dto = AlumnoActividadUpdateDto(nuevoEstado, fechaInscripcion, genero)
//        val resp = api.actualizarEstado(header, alumnoId, actividadId, dto)
//        if (resp.isSuccessful) {
//            cargarAlumnos(actividadId)
//        } else {
//            _error.value = "HTTP ${resp.code()} al actualizar estado"
//        }
//        _loading.value = false
//    }
//
//
//    fun eliminar(actividadId: Int, alumnoId: Int) = viewModelScope.launch {
//        _loading.value = true
//        _error.value   = null
//
//        // Validar token
//        val loginUi = authViewModel.loginState.value
//        if (loginUi !is UiState.Success) {
//            _error.value = "Debes iniciar sesión"
//            _loading.value = false
//            return@launch
//        }
//        val header = "Bearer ${loginUi.data.token}"
//
//        // Llamada DELETE
//        val resp = api.eliminarInscripcion(header, alumnoId, actividadId)
//        if (resp.isSuccessful) {
//            cargarAlumnos(actividadId)
//        } else {
//            _error.value = "HTTP ${resp.code()} al eliminar"
//        }
//        _loading.value = false
//    }
//}

class InscritosViewModel(
    authViewModel: AuthViewModel,
    actividadId: Int,
    private val api: AlumnoActividadApiService
) : ViewModel() {

    private val tokenFlow: Flow<String> = authViewModel.loginState
        .filterIsInstance<UiState.Success<LoginResponseDto>>()
        .map { "Bearer ${it.data.token}" }

    private val _reloadTrigger = MutableSharedFlow<Int>(replay = 1).apply {
        tryEmit(actividadId)
    }


    val alumnosState: StateFlow<UiState<List<AlumnoInscritoDto>>> =
        _reloadTrigger
            .flatMapLatest { id ->
                tokenFlow
                    .take(1)
                    .flatMapLatest { token ->
                        flow {
                            emit(UiState.Loading)
                            val lista = api.obtenerInscritos(token, id)
                            emit(UiState.Success(lista))
                        }
                    }
            }
            .catch { e ->
                emit(UiState.Error(e.message ?: "Error al cargar alumnos"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UiState.Loading
            )

    fun aprobar(actividadId: Int, alumno: AlumnoInscritoDto) {
        realizarCambioEstado(
            actividadId, alumno.alumnoId, nuevoEstado = 4,
            alumno.fechaInscripcion, alumno.genero
        )
    }

    fun reprobar(actividadId: Int, alumno: AlumnoInscritoDto) {
        realizarCambioEstado(
            actividadId, alumno.alumnoId, nuevoEstado = 5,
            alumno.fechaInscripcion, alumno.genero
        )
    }

    fun eliminar(actividadId: Int, alumnoId: Int) {
        viewModelScope.launch {
            val token = tokenFlow.firstOrNull()
            if (token == null) return@launch
            try {
                val resp = api.eliminarInscripcion(token, alumnoId, actividadId)
                if (resp.isSuccessful) {
                    _reloadTrigger.tryEmit(actividadId)
                } else {
                }
            } catch (_: Exception) {  }
        }
    }

    private fun realizarCambioEstado(
        actividadId: Int,
        alumnoId: Int,
        nuevoEstado: Int,
        fechaInscripcion: String,
        genero: Int
    ) {
        viewModelScope.launch {
            val token = tokenFlow.firstOrNull() ?: return@launch
            try {
                val resp = api.actualizarEstado(
                    bearerToken = token,
                    alumnoId    = alumnoId,
                    actividadId = actividadId,
                    nuevoEstado = nuevoEstado
                )
                if (resp.isSuccessful) {
                    _reloadTrigger.tryEmit(actividadId)
                } else {
                }
            } catch (_: Exception) {
            }
        }
    }

}

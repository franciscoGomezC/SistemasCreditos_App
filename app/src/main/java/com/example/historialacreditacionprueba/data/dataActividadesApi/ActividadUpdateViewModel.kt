package com.example.historialacreditacionprueba.data.dataActividadesApi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.historialacreditacionprueba.data.dataActividadesApi.Actividades.ActividadDto
import com.example.historialacreditacionprueba.data.dataActividadesApi.Actividades.ActividadUpdateDto
import com.example.historialacreditacionprueba.data.dataCarreras.Carrera
import com.example.historialacreditacionprueba.data.dataCarreras.RetrofitClientCarrera
import com.example.historialacreditacionprueba.data.dataCarreras.UiState
import com.example.historialacreditacionprueba.data.dataLoginApi.AuthViewModel
import com.example.historialacreditacionprueba.data.dataLoginApi.LoginResponseDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

//class ActividadUpdateViewModel(
//    private val authViewModel: AuthViewModel,
//    private val actividadId: Int
//): ViewModel() {
//
//    //cargar la actividad original
//    private val _original = MutableStateFlow<ActividadDto?>(null)
//    val original: StateFlow<ActividadDto?> = _original
//
//    // cargar las carreras disponibles
//    val carrerasDisponibles = MutableStateFlow<List<Carrera>>(emptyList())
//
//    // lista de ids de carreras seleccionadas
//    private val _carrerasSeleccionadas = MutableStateFlow<List<Int>>(emptyList())
//    val carrerasSeleccionadas: StateFlow<List<Int>> = _carrerasSeleccionadas
//
//    // Campos editables
//    val descripcion     = MutableStateFlow("")
//    val fechaInicioIso  = MutableStateFlow("")
//    val fechaFinIso     = MutableStateFlow("")
//    val creditos        = MutableStateFlow("")
//    val capacidad       = MutableStateFlow("")
//    val estadoActividad = MutableStateFlow(1)
//
//    // UI
//    private val _loading  = MutableStateFlow(false)
//    val loading: StateFlow<Boolean> = _loading
//
//    private val _error    = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?> = _error
//
//    private val _success  = MutableStateFlow(false)
//    val success: StateFlow<Boolean> = _success
//
//    init {
//        viewModelScope.launch {
//            // Carga las carreras
//            try {
//                val lista  = RetrofitClientCarrera.carreraApiService.obtenerCarreras()
//                carrerasDisponibles.value = lista // asigna la lista de carreras
//
//                //loginStateFuncional
//                //validamos que el loginState esté en Success.
//                val loginUi = authViewModel.loginState.value // UIState.Success<LoginResponseDto>
//
//                if(loginUi !is UiState.Success) {
//                    if(loginUi is UiState.Error) {
//                        _error.value = "Error de autenticación: ${loginUi.message}"
//                    } else {
//                        _error.value = "Error de autenticación: Sesión no iniciada o estado inesperado."
//                    }
//                    _loading.value = false // Detener el indicador de carga
//                    return@launch // Retorna solo de esta corrutina (lambda del launch)
//                }
//
//                val token = "Bearer ${loginUi.data.token}"
//
//                //después de tener las carreras, carga la actividad
//                //val token = authViewModel.jwtToken.value ?: throw Exception("Sin token")
//
//                // carga la actividad
//                val dto = RetrofitClientActividad.actividadApiService
//                    .getActividadById("Bearer $token", actividadId)
//                _original.value = dto
//
//                val ids = lista
//                    .filter { carrera -> carrera.nombre in dto.carreraNombres } // compara si el nombre de la carrera está en la lista de nombres de carreras
//                    .map { it.id }
//                _carrerasSeleccionadas.value = ids
//
//                descripcion.value = dto.descripcion
//                fechaInicioIso.value = dto.fechaInicioIso
//                fechaFinIso.value = dto.fechaFinIso
//                creditos.value = dto.creditos.toString()
//                capacidad.value = dto.capacidad.toString()
//                estadoActividad.value = dto.estadoActividad
//            } catch (e: Exception) {
//                _error.value = "No se pudo cargar carreras o la actividad. ${ e.message }"
//            } finally {
//                _loading.value = false // indica que se terminó de cargar
//            }
//        }
//    }
//
//    // Marca/Deselecciona una carrera en el array
//    fun toggleCarrera(id: Int) {
//
//        _carrerasSeleccionadas.value =
//            if (id in _carrerasSeleccionadas.value)
//                _carrerasSeleccionadas.value - id
//            else
//                _carrerasSeleccionadas.value + id
//    }
//
//    fun updateActividad(){
//        viewModelScope.launch {
//
//            _loading.value = true
//            _error.value = null
//
//            try {
//                //loginStateFuncional
//                //validamos que el loginState esté en Success.
//                val loginUi = authViewModel.loginState.value // UIState.Success<LoginResponseDto>
//
//                if(loginUi !is UiState.Success) {
//                    if(loginUi is UiState.Error) {
//                        _error.value = "Error de autenticación: ${loginUi.message}"
//                    } else {
//                        _error.value = "Error de autenticación: Sesión no iniciada o estado inesperado."
//                    }
//                    _loading.value = false // Detener el indicador de carga
//                    return@launch // Retorna solo de esta corrutina (lambda del launch)
//                }
//
//                val token = "Bearer ${loginUi.data.token}"
//                //val token = authViewModel.jwtToken.value ?: throw Exception("Sin token")
//
//                val updDto = ActividadUpdateDto(
//                    descripcion     = descripcion.value,
//                    fechaInicio     = fechaInicioIso.value,
//                    fechaFin        = fechaFinIso.value,
//                    creditos        = creditos.value.toDouble(),
//                    capacidad       = capacidad.value.toInt(),
//                    estadoActividad = estadoActividad.value,
//                    carreraIds      = _carrerasSeleccionadas.value
//                )
//
//                RetrofitClientActividad.actividadApiService
//                    .updateActividad("Bearer $token", actividadId, updDto)
//                _success.value = true
//            } catch (e: Exception) {
//                _error.value = "No se pudo actualizar actividad. ${e.message}"
//            } finally {
//                _loading.value = false
//            }
//        }
//    }
//}

class ActividadUpdateViewModel(
    private val authViewModel: AuthViewModel,
    private val actividadId: Int
) : ViewModel() {

    private val apiAct = RetrofitClientActividad.actividadApiService
    private val apiCarr = RetrofitClientCarrera.carreraApiService

    private val _descripcion    = MutableStateFlow("")
    private val _fechaInicioIso = MutableStateFlow("")
    private val _fechaFinIso    = MutableStateFlow("")
    private val _creditos       = MutableStateFlow("")
    private val _capacidad      = MutableStateFlow("")
    private val _estadoActividad= MutableStateFlow(1)
    private val _carrerasSeleccionadas    = MutableStateFlow<List<Int>>(emptyList())

    val descripcion: StateFlow<String>     = _descripcion
    val fechaInicioIso: StateFlow<String>  = _fechaInicioIso
    val fechaFinIso: StateFlow<String>     = _fechaFinIso
    val creditos: StateFlow<String>        = _creditos
    val capacidad: StateFlow<String>       = _capacidad
    val estadoActividad: StateFlow<Int>    = _estadoActividad
    val carrerasSeleccionadas: StateFlow<List<Int>> = _carrerasSeleccionadas

    private val tokenFlow = authViewModel.loginState
        .filterIsInstance<UiState.Success<LoginResponseDto>>()
        .map { "Bearer ${it.data.token}" }
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), replay = 1)

    private val originalFlow = tokenFlow
        .take(1)
        .flatMapLatest { bearer ->
            flow { emit(apiAct.getActividadById(bearer, actividadId)) }
        }

    /** Un StateFlow “funcional” que carga las carreras en un flow */
    val carrerasDisponibles: StateFlow<List<Carrera>> = flow {
        // Este código está dentro de un bloque flow, así que sí puedes llamar a funciones `suspend`
        val lista = apiCarr.obtenerCarreras()
        emit(lista)
    }
        .catch { e ->
            emit(emptyList())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun onDescripcionChange(nuevaDescripcion: String) {
        _descripcion.value = nuevaDescripcion
    }

    fun onFechaInicioIsoChange(nuevaFecha: String) {
        _fechaInicioIso.value = nuevaFecha
    }

    fun onFechaFinIsoChange(nuevaFecha: String) {
        _fechaFinIso.value = nuevaFecha
    }

    fun onCreditosChange(nuevosCreditos: String) {
        _creditos.value = nuevosCreditos
    }

    fun onCapacidadChange(nuevaCapacidad: String) {
        _capacidad.value = nuevaCapacidad
    }

    fun onEstadoActividadChange(nuevoEstado: Int) {
        _estadoActividad.value = nuevoEstado
    }

    init {
        viewModelScope.launch {
            val dto = originalFlow.first()
            val carreras = carrerasDisponibles.first { it.isNotEmpty() }

            _descripcion.value       = dto.descripcion
            _fechaInicioIso.value    = dto.fechaInicioIso
            _fechaFinIso.value       = dto.fechaFinIso
            _creditos.value          = dto.creditos.toString()
            _capacidad.value         = dto.capacidad.toString()
            _estadoActividad.value   = dto.estadoActividad

            val listaCarr = carreras
            _carrerasSeleccionadas.value =
                listaCarr.filter { dto.carreraNombres.contains(it.nombre) }
                    .map { it.id }
        }
    }

    fun toggleCarrera(id: Int) {
        _carrerasSeleccionadas.value =
            if (id in _carrerasSeleccionadas.value)
                _carrerasSeleccionadas.value - id
            else
                _carrerasSeleccionadas.value + id
    }

    private val _updateTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    fun updateActividad() = _updateTrigger.tryEmit(Unit)

    val updateState: StateFlow<UiState<Unit>> = _updateTrigger
        .flatMapLatest {
            tokenFlow
                .take(1)
                .flatMapLatest { bearer ->
                    flow {
                        emit(UiState.Loading)
                        // validaciones
                        val cred = _creditos.value.toDoubleOrNull()
                            ?: throw IllegalArgumentException("Créditos inválidos")
                        val cap = _capacidad.value.toIntOrNull()
                            ?: throw IllegalArgumentException("Capacidad inválida")

                        val updDto = ActividadUpdateDto(
                            descripcion     = _descripcion.value,
                            fechaInicio     = _fechaInicioIso.value.trim(),
                            fechaFin        = _fechaFinIso.value.trim(),
                            creditos        = cred,
                            capacidad       = cap,
                            estadoActividad = _estadoActividad.value,
                            carreraIds      = _carrerasSeleccionadas.value
                        )

                        apiAct.updateActividad(bearer, actividadId, updDto)
                        emit(UiState.Success(Unit))
                    }.catch { e ->
                        emit(UiState.Error(e.message ?: "Error al actualizar"))
                    }
                }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Idle)
}

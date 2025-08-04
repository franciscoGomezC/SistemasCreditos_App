package com.example.historialacreditacionprueba.data.dataActividadesApi

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.historialacreditacionprueba.data.dataActividadesApi.Actividades.ActividadCreateDto
import com.example.historialacreditacionprueba.data.dataCarreras.Carrera
import com.example.historialacreditacionprueba.data.dataCarreras.RetrofitClientCarrera
import com.example.historialacreditacionprueba.data.dataCarreras.UiState
import com.example.historialacreditacionprueba.data.dataLoginApi.AuthViewModel
import com.example.historialacreditacionprueba.data.dataLoginApi.LoginResponseDto
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

//class ActividadCreateViewModel(
//    private val authViewModel: AuthViewModel
//) : ViewModel() {
//
//    // Campos
//    val nombre        = MutableStateFlow("")
//    val descripcion   = MutableStateFlow("")
//    val fechaInicio   = MutableStateFlow("")  // ISO strings
//    val fechaFin      = MutableStateFlow("")
//    val creditos      = MutableStateFlow("")
//    val capacidad     = MutableStateFlow("")
//    val dias          = MutableStateFlow(1)   // default lunes
//    val horaInicio    = MutableStateFlow("")  // “08:00”
//    val horaFin       = MutableStateFlow("")  // “10:00”
//    val tipoActividad = MutableStateFlow(1)
//    val estadoActividad = MutableStateFlow(1)
//    val imagenNombre  = MutableStateFlow("default.png")
//    val carreraIds    = MutableStateFlow<List<Int>>(emptyList())
//    val genero        = MutableStateFlow(1)
//
//    // Listado de carreras (inyecta CarrerasViewModel o llama directamente)
//    val carreras      = MutableStateFlow<List<Carrera>>(emptyList())
//
//    // Estados UI
//    val loading       = MutableStateFlow(false)
//    val error         = MutableStateFlow<String?>(null)
//    val success       = MutableStateFlow(false)
//
//
//    init {
//        // Carga las carreras
//        viewModelScope.launch {
//            try {
//                carreras.value = RetrofitClientCarrera.carreraApiService.obtenerCarreras()
//            } catch (_: Exception) { /* ignora o setea error */ }
//        }
//    }
//
//    private fun setFechasAhoraYDentroDe2Horas() {
//        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
//        val ahora = Date()
//        val dentroDe2h = Date(ahora.time + 2 * 60 * 60 * 1000)
//        fechaInicio.value = sdf.format(ahora)
//        fechaFin.value    = sdf.format(dentroDe2h)
//    }
//
//    fun createActividad() {
//        Log.d("ActividadVM", "createActividad() invoked, carreras seleccionadas = $carreraIds")
//
//        setFechasAhoraYDentroDe2Horas()
//
//        //loginStateFuncional
//        //validamos que el loginState esté en Success.
//        val loginUi = authViewModel.loginState.value // UIState.Success<LoginResponseDto>
//        if(loginUi !is UiState.Success) {
//            error.value = "Error de autenticación"
//            return
//        }
//
//        val token = "Bearer ${loginUi.data.token}"
//        val deptoId = loginUi.data.departamentoId ?: run {
//            error.value = "Error de autenticación"; return
//        }
//
//        // Validaciones mínimas
//        val cred = creditos.value.toDoubleOrNull() ?: run {
//            error.value = "Créditos inválidos"; return
//        }
//        val cap = capacidad.value.toIntOrNull() ?: run {
//            error.value = "Capacidad inválida"; return
//        }
//
//        val dto = ActividadCreateDto(
//            nombre          = nombre.value,
//            descripcion     = descripcion.value,
//            fechaInicio     = fechaInicio.value,
//            fechaFin        = fechaFin.value,
//            creditos        = cred,
//            capacidad       = cap,
//            dias            = dias.value,
//            horaInicio      = "${horaInicio.value}:00",  // añade segundos
//            horaFin         = "${horaFin.value}:00",
//            tipoActividad   = tipoActividad.value,
//            estadoActividad = estadoActividad.value,
//            imagenNombre    = imagenNombre.value,
//            departamentoId  = deptoId,
//            carreraIds      = carreraIds.value,
//            genero          = genero.value
//        )
//
//        viewModelScope.launch {
//            loading.value = true
//            try {
//                RetrofitClientActividad.actividadApiService
//                    .createActividad(token, dto)
//                success.value = true
//            } catch (e: Exception) {
//                error.value = "Error al crear actividad: ${e.message}"
//            } finally {
//                loading.value = false
//            }
//        }
//    }
//
//    /** Marca/Deselecciona una carrera en el array */
//    fun toggleCarrera(id: Int) {
//        carreraIds.value = if (carreraIds.value.contains(id)) {
//            carreraIds.value - id
//        } else {
//            carreraIds.value + id
//        }
//    }
//}

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class ActividadCreateViewModel(
    private val authViewModel: AuthViewModel
) : ViewModel() {

    val nombre        = MutableStateFlow("")
    val descripcion   = MutableStateFlow("")
    val fechaInicio   = MutableStateFlow("")
    val fechaFin      = MutableStateFlow("")
    val creditos      = MutableStateFlow("")
    val capacidad     = MutableStateFlow("")
    val dias          = MutableStateFlow(1)
    val horaInicio    = MutableStateFlow("")
    val horaFin       = MutableStateFlow("")
    val tipoActividad = MutableStateFlow(1)
    val estadoActividad = MutableStateFlow(1)
    val imagenNombre  = MutableStateFlow("default.png")
    val carreraIds    = MutableStateFlow<List<Int>>(emptyList())
    val genero        = MutableStateFlow(1)

    private val api = RetrofitClientActividad.actividadApiService

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setFechasAhoraYDentroDe2Horas() {
        val ahoraUtc = OffsetDateTime.now(ZoneOffset.UTC)
        val dentroDe2hUtc = ahoraUtc.plusHours(24)

        val isoFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        fechaInicio.value = isoFormatter.format(ahoraUtc)
        fechaFin.value = isoFormatter.format(dentroDe2hUtc)

        horaInicio.value = ahoraUtc.format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()))
        horaFin.value = dentroDe2hUtc.format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()))
    }

    val carrerasState: StateFlow<UiState<List<Carrera>>> = flow {
        emit(UiState.Loading)
        val list = RetrofitClientCarrera.carreraApiService.obtenerCarreras()
        emit(UiState.Success(list))
    }
        .catch { e -> emit(UiState.Error(e.message ?: "Error al cargar carreras")) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Loading)


    private val _createTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    @RequiresApi(Build.VERSION_CODES.O)
    fun createActividad() {
        setFechasAhoraYDentroDe2Horas()
        _createTrigger.tryEmit(Unit)
    }

    private val tokenFlow = authViewModel.loginState
        .filterIsInstance<UiState.Success<LoginResponseDto>>()
        .map { "Bearer ${it.data.token}" }

    val createState: StateFlow<UiState<Unit>> = _createTrigger
        .flatMapLatest {
            tokenFlow
                .take(1)
                .flatMapLatest { bearer ->
                    flow {
                        emit(UiState.Loading)

                        // Validaciones de formulario
                        val cred = creditos.value.toDoubleOrNull()
                            ?: throw IllegalArgumentException("Créditos inválidos")
                        val cap  = capacidad.value.toIntOrNull()
                            ?: throw IllegalArgumentException("Capacidad inválida")

                        // Preparamos DTO
                        val dto = ActividadCreateDto(
                            nombre          = nombre.value,
                            descripcion     = descripcion.value,
                            fechaInicio     = fechaInicio.value.trim(),
                            fechaFin        = fechaFin.value.trim(),
                            creditos        = cred,
                            capacidad       = cap,
                            dias            = dias.value,
                            horaInicio      = "${horaInicio.value}:00",
                            horaFin         = "${horaFin.value}:00",
                            tipoActividad   = tipoActividad.value,
                            estadoActividad = estadoActividad.value,
                            imagenNombre    = imagenNombre.value,
                            departamentoId  = authViewModel.loginState.let {
                                (it.value as? UiState.Success)?.data?.departamentoId
                                    ?: throw IllegalStateException("Sin departamento")
                            },
                            carreraIds      = carreraIds.value,
                            genero          = genero.value
                        )

                        // Llamada al API
                        api.createActividad(bearer, dto)
                        emit(UiState.Success(Unit))
                    }
                        .catch { e ->
                            emit(UiState.Error(e.message ?: "Error al crear"))
                        }
                }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Idle)

    /** Marca/Deselecciona una carrera */
    fun toggleCarrera(id: Int) {
        carreraIds.value =
            if (carreraIds.value.contains(id)) carreraIds.value - id
            else carreraIds.value + id
    }
}

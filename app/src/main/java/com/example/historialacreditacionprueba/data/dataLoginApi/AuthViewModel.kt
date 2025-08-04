package com.example.historialacreditacionprueba.data.dataLoginApi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.historialacreditacionprueba.data.dataCarreras.UiState
import com.example.historialacreditacionprueba.data.dataDepartamentoApi.DepartamentoDto
import com.example.historialacreditacionprueba.data.dataDepartamentoApi.RetrofitClientDepartamento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//class AuthViewModel : ViewModel() {
//
//    val email = MutableStateFlow("")
//    val password = MutableStateFlow("")
//    val confirmPassword = MutableStateFlow("")
//
//    val nombre = MutableStateFlow("")
//    val numeroControl = MutableStateFlow("")
//
//    // Estado para el Departamento
//    private val _departamento = MutableStateFlow<DepartamentoDto?>(null)
//    val departamento: StateFlow<DepartamentoDto?> = _departamento
//
//    private val _loadingDepto = MutableStateFlow(false)
//    val loadingDepto: StateFlow<Boolean> = _loadingDepto
//
//    private val _errorDepto = MutableStateFlow<String?>(null)
//    val errorDepto: StateFlow<String?> = _errorDepto
//
//    val loginError = MutableStateFlow<String?>(null)
//    val loginResponse = MutableStateFlow<LoginResponseDto?>(null)
//    val jwtToken = MutableStateFlow<String?>(null)
//
//    fun onEmailChanged(newEmail: String) { email.value = newEmail }
//    fun onPasswordChanged(newPassword: String) { password.value = newPassword }
//
//    fun login() {
//        viewModelScope.launch {
//            try {
//                val resp = RetrofitClientAuth.authApiService
//                    .login(LoginDto(email.value, password.value))
//                loginResponse.value = resp
//                jwtToken.value = resp.token
//            } catch (e: Exception) {
//                loginError.value = "Credenciales inválidas o error de red"
//            }
//        }
//    }
//
//    fun fetchDepartamento() {
//        val token = loginResponse.value?.token ?: return
//        val deptoId = loginResponse.value?.departamentoId ?: return
//
//        viewModelScope.launch {
//            _loadingDepto.value = true
//            try {
//                val dto = RetrofitClientDepartamento.departamentoApiService
//                    .getDepartamentoById("Bearer $token", deptoId)
//                _departamento.value = dto
//            } catch (e: Exception) {
//                _errorDepto.value = "No se pudo cargar datos del departamento"
//            } finally {
//                _loadingDepto.value = false
//            }
//        }
//    }
//
//    fun cerrarSesion() {
//        email.value = ""
//        password.value = ""
//        confirmPassword.value = ""
//        loginResponse.value = null
//        loginError.value = null
//    }
//
//    private fun isValidEmail(email: String) =
//        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
//
//    private fun isValidPassword(pass: String) = pass.length >= 6
//}

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class AuthViewModel : ViewModel() {
    val email    = MutableStateFlow("")
    val password = MutableStateFlow("")

    private val _loginState = MutableStateFlow<UiState<LoginResponseDto>>(UiState.Idle)
    val loginState: StateFlow<UiState<LoginResponseDto>> = _loginState.asStateFlow()

    private val _navegarAlMenu = MutableStateFlow(false)
    val navegarAlMenu: StateFlow<Boolean> = _navegarAlMenu

    fun login() {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            try {
                val dto = LoginDto(email.value, password.value)
                val resp = RetrofitClientAuth.authApiService.login(dto)
                _loginState.value = UiState.Success(resp)
                _navegarAlMenu.value = true
            } catch (e: Exception) {
                _loginState.value = UiState.Error(e.message ?: "Error de red o credenciales")
            }
        }
    }

    private val deptoTrigger: Flow<Pair<String, Int>> =
        loginState
            .filterIsInstance<UiState.Success<LoginResponseDto>>()
            .map { it.data }
            .map { resp -> "Bearer ${resp.token}" to (resp.departamentoId ?: 0) }

    val departamentoState: StateFlow<UiState<DepartamentoDto>> =
        deptoTrigger
            .flatMapLatest { (token, deptoId) ->
                flow {
                    emit(UiState.Loading)
                    val info = RetrofitClientDepartamento.departamentoApiService
                        .getDepartamentoById(token, deptoId)
                    emit(UiState.Success(info))
                }
                    .flowOn(Dispatchers.IO)
            }
            .catch { e -> emit(UiState.Error(e.message ?: "Error cargando departamento")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UiState.Idle
            )

    fun cerrarSesion() {
        email.value = ""
        password.value = ""
        _loginState.value = UiState.Idle
        _navegarAlMenu.value = false
    }
}
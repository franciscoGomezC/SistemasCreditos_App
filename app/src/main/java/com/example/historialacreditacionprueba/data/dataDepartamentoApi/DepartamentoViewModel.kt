package com.example.historialacreditacionprueba.data.dataDepartamentoApi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.historialacreditacionprueba.data.dataCarreras.UiState
import com.example.historialacreditacionprueba.data.dataLoginApi.AuthViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DepartamentoViewModel(
    private val authViewModel: AuthViewModel
) : ViewModel() {

    val departamento = MutableStateFlow<DepartamentoDto?>(null)
    val loading = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)

    fun fetchDepartamento() {

        //loginStateFuncional
        //validamos que el loginState esté en Success.
        val loginUi = authViewModel.loginState.value // UIState.Success<LoginResponseDto>
        if(loginUi !is UiState.Success) {
            error.value = "Error de autenticación"
            return
        }

        val token = "Bearer ${loginUi.data.token}"
        val deptoId = loginUi.data.departamentoId ?: run {
            error.value = "Error de autenticación"; return
        }

//        val token = authViewModel.jwtToken.value ?: return
//        val deptoId = authViewModel.loginResponse.value?.departamentoId ?: return

        viewModelScope.launch {
            loading.value = true
            try {
                val dto = RetrofitClientDepartamento.departamentoApiService
                    .getDepartamentoById("Bearer $token", deptoId)
                departamento.value = dto
            } catch (e: Exception) {
                error.value = "No se pudo cargar datos del departamento"
            } finally {
                loading.value = false
            }
        }
    }
}

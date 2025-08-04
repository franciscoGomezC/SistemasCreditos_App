package com.example.historialacreditacionprueba.data.dataLoginApi

import kotlinx.serialization.Serializable

data class LoginDto(
    val usuario: String,
    val password: String
)

data class LoginResponseDto(
    val token: String,
    val expiration: String,
    val alumnoId: String? = null,
    val departamentoId: Int? = null,
    val coordinadorId: String?
)

data class RegisterDto(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val numeroControl: String,
    val nombre: String,
    val apellido: String,
    val semestre: Int,
    val totalCreditos: Double,
    val carreraId: Int
)

package com.example.historialacreditacionprueba.data.dataLoginApi

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/Auth/login")
    suspend fun login(@Body loginDto: LoginDto): LoginResponseDto

    @POST("api/Auth/register")
    suspend fun registerAlumno(@Body registerDto: RegisterDto): String
}

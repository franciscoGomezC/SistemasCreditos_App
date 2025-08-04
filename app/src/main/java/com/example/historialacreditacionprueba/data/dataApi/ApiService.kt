package com.example.historialacreditacionprueba.data.dataApi
import retrofit2.http.GET

interface ApiService {
    @GET("comments")
    suspend fun getComments(): List<Comment>
}

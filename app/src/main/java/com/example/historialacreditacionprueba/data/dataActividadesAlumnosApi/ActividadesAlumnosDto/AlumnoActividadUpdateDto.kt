package com.example.historialacreditacionprueba.data.dataActividadesAlumnosApi.ActividadesAlumnosDto

data class AlumnoActividadUpdateDto(
    val estadoAlumnoActividad: Int,
    val fechaInscripcion: String, // ISO-8601, ej. "2025-07-28T10:00:00Z"
    val genero: Int
)

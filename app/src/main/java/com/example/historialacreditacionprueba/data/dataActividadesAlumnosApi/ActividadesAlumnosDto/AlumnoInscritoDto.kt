package com.example.historialacreditacionprueba.data.dataActividadesAlumnosApi.ActividadesAlumnosDto

data class AlumnoInscritoDto(

    val alumnoId: Int,
    val nombreCompleto: String,
    val carreraNombre: String,
    val semestre: Int,
    val creditosObtenidos : Double,
    val fechaInscripcion: String,
    val estadoAlumnoActividad: Int,
    val genero: Int

)

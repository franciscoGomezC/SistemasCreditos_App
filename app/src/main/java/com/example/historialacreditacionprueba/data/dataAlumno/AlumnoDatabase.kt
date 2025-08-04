package com.example.historialacreditacionprueba.data.dataAlumno

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alumno(

    @PrimaryKey val numeroControl: String,

    val nombre: String,

    val creditos: Float,

    val carrera: String
)

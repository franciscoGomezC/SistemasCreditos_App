package com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos

import androidx.room.Entity
import androidx.room.Index

// Entidad que representa la relación entre Alumno y Curso
@Entity(
    primaryKeys = ["numeroControl", "cursoId"],
    indices = [Index(value = ["cursoId"]), Index(value = ["numeroControl"])]
)
data class AlumnoCursoCrossRef(
    val numeroControl: String, //fk hacia alumno
    val cursoId: Int           //fk hacia curso
)


/*

@Entity(primaryKeys = ["numeroControl", "cursoId"])
data class AlumnoCursoCrossRef(
    val numeroControl: String, // FK hacia Alumno
    val cursoId: Int           // FK hacia Curso
)

 */
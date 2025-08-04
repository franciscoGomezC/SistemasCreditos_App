package com.example.historialacreditacionprueba.data.dataCurso

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.historialacreditacionprueba.R


// La anotación @Entity indica que esta clase representará una tabla en la base de datos Room.
@Entity
data class Curso(

    @PrimaryKey(autoGenerate = true) val cursoId: Int = 0,

    val nombreCurso: String,

    val descripcion: String,

    val creditos: Float,

    val fechaInicio: String,

    val fechaFin: String,

    val categoria: String,
    val imagen: Int = R.drawable
        .cursos4 // Valor predeterminado
)

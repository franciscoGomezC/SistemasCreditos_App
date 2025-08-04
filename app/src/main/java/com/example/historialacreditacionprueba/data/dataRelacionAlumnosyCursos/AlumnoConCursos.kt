package com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.historialacreditacionprueba.data.dataAlumno.Alumno
import com.example.historialacreditacionprueba.data.dataCurso.Curso

//relación muchos a muchos entre Alumno y Curso
// se crea una tabla intermedia AlumnoCursoCrossRef
// para almacenar la relación entre los alumnos y los cursos
// se crea una clase AlumnoConCursos que contiene una lista de cursos

data class AlumnoConCursos(
    @Embedded val alumno: Alumno, // embedded para que se incluya en la relación

    @Relation(
        parentColumn = "numeroControl", // del Alumno
        entityColumn = "cursoId", // del Curso
        associateBy = Junction(AlumnoCursoCrossRef::class)
    )
    val cursos: List<Curso> // lista de cursos
)


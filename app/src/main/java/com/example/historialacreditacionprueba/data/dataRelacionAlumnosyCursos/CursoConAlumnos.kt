package com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.historialacreditacionprueba.data.dataAlumno.Alumno
import com.example.historialacreditacionprueba.data.dataCurso.Curso

//relación muchos a muchos entre Alumno y Curso
// se crea una tabla intermedia AlumnoCursoCrossRef
// para almacenar la relación entre los alumnos y los cursos
// se crea una clase CursoConAlumnos que contiene una lista de alumnos

data class CursoConAlumnos(
    @Embedded val curso: Curso,
    @Relation(
        parentColumn = "cursoId",
        entityColumn = "numeroControl",
        associateBy = Junction(AlumnoCursoCrossRef::class)
    )
    val alumnos: List<Alumno>
)

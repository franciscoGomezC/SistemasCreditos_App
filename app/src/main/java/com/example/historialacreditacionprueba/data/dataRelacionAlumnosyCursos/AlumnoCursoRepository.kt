package com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos

import com.example.historialacreditacionprueba.data.dataAlumno.AlumnoDao
import kotlinx.coroutines.flow.Flow

class AlumnoCursoRepository(private val alumnoCursoDao: AlumnoCursoDao) {

    suspend fun inscribirAlumnoACurso(crossRef: AlumnoCursoCrossRef) {
        alumnoCursoDao.insertarAlumnoCursoCrossRef(crossRef)
    }

    //se elimina el alumno de un curso
    suspend fun darseDeBajaDeCurso(crossRef: AlumnoCursoCrossRef) {
        alumnoCursoDao.eliminarAlumnoCursoCrossRef(crossRef)
    }

    //muestra la lista de cursos de un alumno
    fun obtenerCursosDeAlumno(numeroControl: String): Flow<List<AlumnoConCursos>> {
        return alumnoCursoDao.obtenerCursosDeAlumno(numeroControl) //se cambió a string
    }

    //muestra la lista de alumnos de un curso
    fun obtenerAlumnosDeCurso(cursoId: Int): Flow<List<CursoConAlumnos>> {
        return alumnoCursoDao.obtenerAlumnosDeCurso(cursoId)
    }



}

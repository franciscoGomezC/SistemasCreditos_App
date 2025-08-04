package com.example.historialacreditacionprueba.data.dataAlumno

import com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos.AlumnoConCursos
import kotlinx.coroutines.flow.Flow

class OfflineAlumnosRepository(private val alumnoDao: AlumnoDao) : AlumnosRepository {

    override fun getAllAlumnos() = alumnoDao.getAlumnos() //se encarga de obtener todos los alumnos de la base de datos
    override suspend fun getAlumno(numeroControl: String): Flow<Alumno> = alumnoDao.obtenerAlumnoPorId(numeroControl) //se encarga de obtener un alumno por su id

    override suspend fun insertAlumno(alumno: Alumno) = alumnoDao.insertarAlumno(alumno) //se encarga de insertar un alumno en la base de datos
    override suspend fun updateAlumno(alumno: Alumno) = alumnoDao.actualizarAlumno(alumno) //se encarga de actualizar un alumno en la base de datos
    override suspend fun deleteAlumno(alumno: Alumno) = alumnoDao.eliminarAlumno(alumno) //se encarga de eliminar un alumno de la base de datos
    override fun obtenerAlumnosConCursos(): Flow<List<AlumnoConCursos>> = alumnoDao.obtenerAlumnosConCursos() //se encarga de obtener todos los alumnos con sus cursos asociados
}
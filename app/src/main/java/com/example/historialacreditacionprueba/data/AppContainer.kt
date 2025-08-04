package com.example.historialacreditacionprueba.data

import android.content.Context
import com.example.historialacreditacionprueba.data.dataAlumno.AlumnosRepository
import com.example.historialacreditacionprueba.data.dataAlumno.OfflineAlumnosRepository
import com.example.historialacreditacionprueba.data.dataCurso.CursosRepository
import com.example.historialacreditacionprueba.data.dataCurso.OfflineCursosRepository
import com.example.historialacreditacionprueba.data.dataLogin.UsuariosRepository
import com.example.historialacreditacionprueba.data.dataLogin.UsuariosRepositoryImpl
import com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos.AlumnoCursoDao
import com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos.AlumnoCursoRepository

interface AppContainer {
    //se llaman en el MainActivity
    val alumnosRepository: AlumnosRepository // Declaración de un repositorio de alumnos
    val cursosRepository: CursosRepository // Declaración de un repositorio de cursos
    val usuarioRepository: UsuariosRepository // Declaración de un repositorio de usuarios
    val alumnoCursoRepository: AlumnoCursoRepository
}

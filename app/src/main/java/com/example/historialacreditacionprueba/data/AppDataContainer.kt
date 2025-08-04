package com.example.historialacreditacionprueba.data

import android.content.Context
import com.example.historialacreditacionprueba.data.dataAlumno.AlumnosRepository
import com.example.historialacreditacionprueba.data.dataAlumno.OfflineAlumnosRepository
import com.example.historialacreditacionprueba.data.dataCurso.CursosRepository
import com.example.historialacreditacionprueba.data.dataCurso.OfflineCursosRepository
import com.example.historialacreditacionprueba.data.dataLogin.UsuariosRepository
import com.example.historialacreditacionprueba.data.dataLogin.UsuariosRepositoryImpl
import com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos.AlumnoCursoRepository

class AppDataContainer(private val context: Context) : AppContainer { // Interfaz AppContainer

    private val database = AppDatabase.getDatabase(context)

    override val alumnosRepository: AlumnosRepository by lazy {
        OfflineAlumnosRepository(database.alumnoDao())
    }

    override val cursosRepository: CursosRepository by lazy {
        OfflineCursosRepository(database.cursoDao())
    }

    override val alumnoCursoRepository: AlumnoCursoRepository by lazy {
        AlumnoCursoRepository(database.alumnoCursoDao())
    }

    override val usuarioRepository: UsuariosRepository by lazy {
        UsuariosRepositoryImpl(database.usuarioDao())
    }

}
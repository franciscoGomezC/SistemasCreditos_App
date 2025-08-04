package com.example.historialacreditacionprueba.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.historialacreditacionprueba.data.dataAlumno.AlumnoDao
import com.example.historialacreditacionprueba.data.dataCurso.CursoDao
import com.example.historialacreditacionprueba.data.dataAlumno.Alumno
import com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos.AlumnoCursoCrossRef
import com.example.historialacreditacionprueba.data.dataCurso.Curso
import com.example.historialacreditacionprueba.data.dataLogin.Usuario
import com.example.historialacreditacionprueba.data.dataLogin.UsuarioDao
import com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos.AlumnoCursoDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(
    entities = [Alumno::class, Curso::class, AlumnoCursoCrossRef::class, Usuario::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alumnoDao(): AlumnoDao
    abstract fun cursoDao(): CursoDao
    abstract fun alumnoCursoDao(): AlumnoCursoDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {

                    })
                    .build()
                    .also { Instance = it }
            }
        }


    }
}
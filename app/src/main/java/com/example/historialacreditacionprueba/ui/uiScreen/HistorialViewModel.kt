package com.example.historialacreditacionprueba.ui.uiScreen//package com.example.historialacreditacionprueba.ui.uiScreen
//
//import android.util.Patterns
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.historialacreditacionprueba.data.dataAlumno.Alumno
//import com.example.historialacreditacionprueba.data.dataAlumno.AlumnosRepository
//import com.example.historialacreditacionprueba.data.dataCurso.Curso
//import com.example.historialacreditacionprueba.data.dataCurso.CursosRepository
//import com.example.historialacreditacionprueba.data.dataLogin.Usuario
//import com.example.historialacreditacionprueba.data.dataLogin.UsuariosRepository
//import com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos.AlumnoConCursos
//import com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos.AlumnoCursoCrossRef
//import com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos.AlumnoCursoDao
//import com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos.AlumnoCursoRepository
//import com.example.historialacreditacionprueba.data.dataRelacionAlumnosyCursos.CursoConAlumnos
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.combine
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.firstOrNull
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.launch
//
////StateFlow siempre almacena el último valor y lo emite a los observadores cuando cambia
//
//class HistorialViewModel(
//    private val alumnoRepository: AlumnosRepository,
//    private val cursosRepository: CursosRepository,
//    private val usuarioRepository: UsuariosRepository,
//    private val alumnoCursoRepository: AlumnoCursoRepository
//) : ViewModel() {
//
//    // ═══════════════════════════════════════════
//    // 1. Estados Generales
//    // ═══════════════════════════════════════════
//
//    private val _mensaje = MutableStateFlow("")
//    val mensaje: StateFlow<String> = _mensaje.asStateFlow()
//
//    private val _cursos = MutableStateFlow<List<Curso>>(emptyList())
//    val cursos: StateFlow<List<Curso>> = _cursos.asStateFlow()
//
//    private val _alumnosPorCurso = MutableStateFlow<List<CursoConAlumnos>>(emptyList())
//    val alumnosPorCurso: StateFlow<List<CursoConAlumnos>> = _alumnosPorCurso
//
//    private val _cursosInscritos = MutableStateFlow<List<AlumnoConCursos>>(listOf())
//
//    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
//    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()
//
//    // ═══════════════════════════════════════════
//    // 2. Autenticación y Usuario
//    // ═══════════════════════════════════════════
//
//    private val _email = MutableStateFlow("")
//    val email: StateFlow<String> get() = _email
//
//    private val _password = MutableStateFlow("")
//    val password: StateFlow<String> get() = _password
//
//    private val _loginError = MutableStateFlow<String?>(null)
//    val loginError: StateFlow<String?> = _loginError
//
//    val loginEnable: StateFlow<Boolean> = combine(_email, _password) { email, password ->
//        isValidEmail(email) && isValidPassword(password)
//    }.stateIn(
//        viewModelScope,
//        SharingStarted.WhileSubscribed(5000),
//        false
//    )
//
//    fun onEmailChanged(email: String) {
//        _email.value = email
//    }
//
//    fun onPasswordChanged(password: String) {
//        _password.value = password
//    }
//
//    fun loginUsuario(
//        correo: String,
//        contraseña: String,
//        onResult: (Usuario, Alumno?) -> Unit,
//        onError: () -> Unit
//    ) {
//        viewModelScope.launch {
//            val usuario = usuarioRepository.login(correo, contraseña)
//            if (usuario != null) {
//                _usuarioActual.value = usuario
//                _loginError.value = null
//                obtenerCursos() //se cargan los cursos
//
//                if (usuario.tipoUsuario == "alumno") {
//                    val alumno = usuarioRepository.obtenerAlumnoPorNumeroControlInicioSesion(usuario.idUsuario)
//                    onResult(usuario, alumno)
//                } else {
//                    onResult(usuario, null)
//                }
//            } else {
//                _loginError.value = null
//                onError()
//            }
//        }
//    }
//
//    private fun isValidPassword(password: String): Boolean = password.length > 6
//    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
//
//    // ═══════════════════════════════════════════
//    // 3. Cursos (CRUD y gestión)
//    // ═══════════════════════════════════════════
//
//    init {
//        obtenerCursos()
//    }
//
//    private fun obtenerCursos() {
//        viewModelScope.launch {
//            cursosRepository.getAllCursos().collect {
//                _cursos.value = it
//            }
//        }
//    }
//
//    fun insertarCurso(curso: Curso) {
//        viewModelScope.launch {
//            cursosRepository.insertarCurso(curso)
//        }
//    }
//
//    fun updateCurso(cursoActualizado: Curso) {
//        viewModelScope.launch {
//            cursosRepository.updateCurso(cursoActualizado)
//        }
//    }
//
//    fun deleteCurso(curso: Curso) {
//        viewModelScope.launch {
//            cursosRepository.deleteCurso(curso)
//        }
//    }
//
//    fun agregarCursoDisponible(curso: Curso) {
//        if (_cursos.value.none { it.cursoId == curso.cursoId }) {
//            _cursos.value = _cursos.value + curso
//        }
//    }
//
//    // ═══════════════════════════════════════════
//    // 4. Alumnos y Cursos (Relaciones)
//    // ═══════════════════════════════════════════
//
//    fun obtenerAlumnosInscritos(cursoId: Int) {
//        viewModelScope.launch {
//            alumnoCursoRepository.obtenerAlumnosDeCurso(cursoId).collect {
//                _alumnosPorCurso.value = it
//            }
//        }
//    }
//
//    fun cargarCursosInscritos(numeroControl: String) {
//        viewModelScope.launch {
//            alumnoCursoRepository.obtenerCursosDeAlumno(numeroControl).collect {
//                _cursosInscritos.value = it
//            }
//        }
//    }
//
//    fun inscribirAlumnoACurso(numeroControl: String, cursoId: Int) {
//        val curso = _cursos.value.find { it.cursoId == cursoId } ?: return
//        viewModelScope.launch {
//            try {
//                val alumnoCursos = alumnoCursoRepository.obtenerCursosDeAlumno(numeroControl).first()
//                val cursosActuales = alumnoCursos.firstOrNull()?.cursos ?: emptyList()
//                val totalCreditosActuales = cursosActuales.sumOf { it.creditos.toDouble() }
//                val nuevoTotalCreditos = totalCreditosActuales + curso.creditos
//
//                if (nuevoTotalCreditos > 5.0) {
//                    _mensaje.value = "No puedes inscribirte, excederías los 5 créditos."
//                } else {
//                    // Inscribir al curso
//                    alumnoCursoRepository.inscribirAlumnoACurso(AlumnoCursoCrossRef(numeroControl, cursoId))
//
//                    // Obtener el alumno actual de la BD (usa el repositorio correcto)
//                    val alumno = alumnoRepository.getAlumno(numeroControl).firstOrNull()
//                    if (alumno != null) {
//                        // Crear copia actualizada con nuevos créditos
//                        val alumnoActualizado = alumno.copy(creditos = nuevoTotalCreditos.toFloat())
//                        alumnoRepository.updateAlumno(alumnoActualizado)
//                    }
//
//                    _mensaje.value = "¡Inscripción realizada con éxito!"
//                    cargarCursosInscritos(numeroControl)
//                }
//            } catch (e: Exception) {
//                _mensaje.value = "Error al inscribir: ${e.message}"
//            }
//        }
//    }
//
//    suspend fun darseDeBaja(numeroControl: String, curso: Curso) {
//        try {
//            // 1. Eliminar la relación entre alumno y curso
//            alumnoCursoRepository.darseDeBajaDeCurso(AlumnoCursoCrossRef(numeroControl, curso.cursoId))
//
//            // 2. Obtener el alumno actual desde la base de datos
//            val alumno = alumnoRepository.getAlumno(numeroControl).firstOrNull()
//
//            if (alumno != null) {
//                // 3. Restar los créditos del curso
//                val nuevosCreditos = alumno.creditos - curso.creditos
//
//                // 4. Actualizar los créditos del alumno
//                val alumnoActualizado = alumno.copy(creditos = nuevosCreditos)
//                alumnoRepository.updateAlumno(alumnoActualizado)
//            }
//
//            // 5. Actualizar UI
//            _cursosInscritos.value = _cursosInscritos.value.map {
//                if (it.alumno.numeroControl == numeroControl) {
//                    it.copy(cursos = it.cursos.filter { c -> c.cursoId != curso.cursoId })
//                } else it
//            }
//
//            _mensaje.value = "Te diste de baja correctamente del curso."
//
//        } catch (e: Exception) {
//            _mensaje.value = "Error al darse de baja: ${e.message}"
//        }
//    }
//
//
//    fun obtenerCursosDisponiblesParaAlumno(numeroControl: String): StateFlow<List<Curso>> {
//        return _cursos.combine(_cursosInscritos) { todos, inscritos ->
//            val cursosAlumno = inscritos.find { it.alumno.numeroControl == numeroControl }?.cursos?.map { it.cursoId } ?: emptyList()
//            todos.filter { it.cursoId !in cursosAlumno }
//        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
//    }
//
//    fun obtenerCursosDeAlumno(numeroControl: String): Flow<List<AlumnoConCursos>> {
//        return alumnoCursoRepository.obtenerCursosDeAlumno(numeroControl)
//    }
//
//    // ═══════════════════════════════════════════
//    // 5. Auxiliares
//    // ═══════════════════════════════════════════
//
//    fun limpiarMensaje() {
//        _mensaje.value = ""
//    }
//}

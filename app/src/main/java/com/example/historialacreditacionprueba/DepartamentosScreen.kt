package com.example.historialacreditacionprueba

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.historialacreditacionprueba.data.dataApi.CommentViewModel
import com.example.historialacreditacionprueba.ui.theme.HistorialAcreditacionPruebaTheme
import com.example.historialacreditacionprueba.data.dataCarreras.CarrerasViewModel
import com.example.historialacreditacionprueba.data.dataLoginApi.AuthViewModel
import com.example.historialacreditacionprueba.ui.uiScreen.screenCurso.AgregarActividadScreen
import com.example.historialacreditacionprueba.ui.uiScreen.HistorialViewModel
import com.example.historialacreditacionprueba.ui.uiScreen.LoginScreen
import com.example.historialacreditacionprueba.ui.uiScreen.ScreenApiCarrera.CarrerasScreen
import com.example.historialacreditacionprueba.ui.uiScreen.screenDepartamento.MenuPrincipal
import com.example.historialacreditacionprueba.ui.uiScreen.screenAlumno.PantallaCursosAlumno
import com.example.historialacreditacionprueba.ui.uiScreen.screenAlumno.PantallaInicioAlumno
import com.example.historialacreditacionprueba.ui.uiScreen.screenAlumno.PantallaMisCursos
//import com.example.historialacreditacionprueba.ui.uiScreen.screenAlumno.PerfilAlumnoScreen
import com.example.historialacreditacionprueba.ui.uiScreen.screenCurso.DetalleCurso
import com.example.historialacreditacionprueba.ui.uiScreen.screenCurso.EditarCursoScreen
import com.example.historialacreditacionprueba.ui.uiScreen.screenCurso.ListaAlumnosScreen
import com.example.historialacreditacionprueba.ui.uiScreen.screenCurso.MostrarCursos
import com.example.historialacreditacionprueba.ui.uiScreen.screenApi.CommentsScreen
import com.example.historialacreditacionprueba.ui.uiScreen.screenDepartamento.ActividadesScreen
import com.example.historialacreditacionprueba.ui.uiScreen.screenDepartamento.CrearActividadApiScreen
import com.example.historialacreditacionprueba.ui.uiScreen.screenDepartamento.EditActividadScreen
import com.example.historialacreditacionprueba.ui.uiScreen.screenDepartamento.InscritosScreen

//import com.example.historialacreditacionprueba.uiScreen.PantallaHistorialAcreditacion

/*
* Valores enum que representen las pantallas en la app
*/

enum class DepartamentosScreen() {
    //Login,
    MenuPrincipal,
    CursosCreados,
    HistorialAlumno,
    AgregarActividad,

    //alumno
    InicioAlumno,
    Comentarios,
    Carreras,

    LoginApi,
    CrearActividadApi,
    ActividadesApi,
    EditarActividadesApi,
    InscritosApi
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DepartamentoApp(
    historialViewModel: HistorialViewModel,
    //commentViewModel: CommentViewModel
) {
    val navController: NavHostController = rememberNavController()
    val commentViewModel: CommentViewModel = viewModel()
    val carrerasViewModel: CarrerasViewModel = viewModel()
    val loginApiAuthViewModel: AuthViewModel = viewModel()


    HistorialAcreditacionPruebaTheme {
        Surface(modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background) {
            NavHost(navController = navController,
                startDestination = DepartamentosScreen.LoginApi.name
            ) {

                //login de la api
                composable(DepartamentosScreen.LoginApi.name) {
                    LoginScreen(navController, loginApiAuthViewModel)
                }

                //crear actividad de la api
                composable(DepartamentosScreen.CrearActividadApi.name) {
                    CrearActividadApiScreen(navController, loginApiAuthViewModel)
                }

                composable(DepartamentosScreen.ActividadesApi.name) {
                    ActividadesScreen(navController, loginApiAuthViewModel)
                }

                composable(
                    route = "${DepartamentosScreen.EditarActividadesApi.name}/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->

                    val id = backStackEntry.arguments?.getInt("id") ?: return@composable // recupera el id de la ruta

                    EditActividadScreen(navController, loginApiAuthViewModel, id)
                }

                composable(
                    route = "${DepartamentosScreen.InscritosApi.name}/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ){
                    backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("id") ?: return@composable
                    InscritosScreen(navController, loginApiAuthViewModel, id)
                }

//                //Login general para el departamento y el alumno
//                composable(DepartamentosScreen.Login.name) {
//                    LoginScreen(navController, historialViewModel)
//                }

                //Menu principal para el departamento
                composable(DepartamentosScreen.MenuPrincipal.name) {
                    MenuPrincipal(navController, loginApiAuthViewModel)
                }

                //Mostrar cursos del departamento
                //esta es la vista previa de los cursos que ha subido el departamento
                composable(DepartamentosScreen.CursosCreados.name) {
                    MostrarCursos(historialViewModel, navController)
                }

                //el departamento agrega los cursos
                composable(DepartamentosScreen.AgregarActividad.name) {
                    AgregarActividadScreen(navController, historialViewModel)
                }

                composable("detalleCurso/{cursoId}") { backStackEntry ->
                    val cursoId = backStackEntry.arguments?.getString("cursoId")?.toIntOrNull()
                    if (cursoId != null) {
                        val cursos by historialViewModel.cursos.collectAsState()
                        val curso = cursos.find { it.cursoId == cursoId }

                        curso?.let {
                            DetalleCurso(it, historialViewModel, navController)
                        } ?: Text("Curso no encontrado.")
                    }
                }

                //pantalla para editar el curso
                composable("editarCurso/{cursoId}") { backStackEntry ->
                    val cursoId = backStackEntry.arguments?.getString("cursoId")?.toIntOrNull()
                    if (cursoId != null) {
                        EditarCursoScreen(cursoId = cursoId, viewModel = historialViewModel, navController = navController)
                    } else {
                        Text("ID inválido.")
                    }
                }

                //esta es la lista de alumnos para cada curso creado
                composable("listaAlumnos/{cursoId}") { backStackEntry ->
                    val cursoId = backStackEntry.arguments?.getString("cursoId")?.toIntOrNull() ?: 0
                    ListaAlumnosScreen(
                        cursoId = cursoId,
                        viewModel = historialViewModel,
                        navController = navController
                    )
                }

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                //Es la pantalla del inicio del alumno
                composable(DepartamentosScreen.InicioAlumno.name) {
                    PantallaInicioAlumno(navController = navController, historialViewModel)
                }

                //esta pantalla muestra todos los cursos que se han creado por parte del departamento
                composable(
                    route = "inicio_alumno/{numeroControl}",
                    arguments = listOf(navArgument("numeroControl") { type = NavType.StringType })
                ) { backStackEntry ->
                    val numeroControl = backStackEntry.arguments?.getString("numeroControl") ?: ""
                    PantallaCursosAlumno(
                        navController = navController,
                        viewModel = historialViewModel,
                        numeroControl = numeroControl
                    )
                }

                // Perfil del alumno (requiere numeroControl)
//                composable(
//                    route = "perfil_alumno/{numeroControl}",
//                    arguments = listOf(navArgument("numeroControl") { type = NavType.StringType })
//                ) { backStackEntry ->
//                    val numeroControl = backStackEntry.arguments?.getString("numeroControl") ?: ""
//                    PerfilAlumnoScreen(
//                        viewModel = historialViewModel,
//                        numeroControl = numeroControl,
//                        navController = navController
//                    )
//                }

                // Mis cursos (requiere numeroControl)
                //se muestran los cursos a los que el alumno se ha inscribido.
                composable(
                    route = "mis_cursos/{numeroControl}",
                    arguments = listOf(navArgument("numeroControl") { type = NavType.StringType })
                ) { backStackEntry ->
                    val numeroControl = backStackEntry.arguments?.getString("numeroControl") ?: ""
                    PantallaMisCursos(
                        navController = navController,
                        viewModel = historialViewModel,
                        numeroControl = numeroControl
                    )
                }

                //##################################################################################
                //Api
                composable(DepartamentosScreen.Comentarios.name) {
                    CommentsScreen(viewModel = commentViewModel, navController = navController)
                }

                composable(DepartamentosScreen.Carreras.name) {
                    CarrerasScreen(navController = navController, viewModel = carrerasViewModel)
                    }

            }
        }
    }
}

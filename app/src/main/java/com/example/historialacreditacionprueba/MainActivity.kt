package com.example.historialacreditacionprueba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.historialacreditacionprueba.data.AppDataContainer
import com.example.historialacreditacionprueba.ui.theme.HistorialAcreditacionPruebaTheme
import com.example.historialacreditacionprueba.ui.uiScreen.HistorialViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Crea manualmente el AppContainer
        val appContainer = AppDataContainer(applicationContext)

        // Crea manualmente el ViewModel con los repositorios
        val historialViewModel = HistorialViewModel(
            appContainer.alumnosRepository,
            appContainer.cursosRepository,
            appContainer.usuarioRepository,
            appContainer.alumnoCursoRepository
        )

        setContent {
            HistorialAcreditacionPruebaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                )
                {
                    DepartamentoApp(historialViewModel = historialViewModel)
                }
            }
        }
    }
}

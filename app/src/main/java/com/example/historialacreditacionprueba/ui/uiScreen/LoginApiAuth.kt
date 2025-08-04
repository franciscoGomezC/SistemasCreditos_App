package com.example.historialacreditacionprueba.ui.uiScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.historialacreditacionprueba.DepartamentosScreen
import com.example.historialacreditacionprueba.data.dataCarreras.UiState
import com.example.historialacreditacionprueba.data.dataLoginApi.AuthViewModel

//@Composable
//fun LoginScreen(navController: NavController, viewModel: AuthViewModel) {
//    val email by viewModel.email.collectAsStateWithLifecycle()
//    val password by viewModel.password.collectAsStateWithLifecycle()
//    val loginError by viewModel.loginError.collectAsStateWithLifecycle()
//    val loginResponse by viewModel.loginResponse.collectAsStateWithLifecycle()
//
//    val passwordVisible = rememberSaveable { mutableStateOf(false) }
//    val focusManager = LocalFocusManager.current
//    val scrollState = rememberScrollState()
//
//    // Navegar si se detecta un login exitoso
//    LaunchedEffect(key1 = loginResponse) {
//        loginResponse?.let {
//            if (it.alumnoId != null) {
//                navController.navigate(DepartamentosScreen.InicioAlumno.name)
//                {
//                    popUpTo(DepartamentosScreen.Login.name) { inclusive = true }
//                }
//            } else {
//                navController.navigate(DepartamentosScreen.MenuPrincipal.name)
//                {
//                    popUpTo(DepartamentosScreen.Login.name) { inclusive = true }
//                }
//            }
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//            .padding(24.dp)
//            .verticalScroll(scrollState),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text("Instituto Tecnológico de Chetumal", style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
//        Spacer(modifier = Modifier.height(12.dp))
//
//        Icon(Icons.Default.AccountCircle, contentDescription = "Logo", modifier = Modifier.size(150.dp), tint = MaterialTheme.colorScheme.primary)
//        Spacer(modifier = Modifier.height(24.dp))
//        Text("Inicio de Sesión", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
//        Spacer(modifier = Modifier.height(20.dp))
//
//        OutlinedTextField(
//            value = email,
//            onValueChange = viewModel::onEmailChanged,
//            label = { Text("Correo electrónico") },
//            modifier = Modifier.fillMaxWidth(),
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
//            singleLine = true
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        OutlinedTextField(
//            value = password,
//            onValueChange = viewModel::onPasswordChanged,
//            label = { Text("Contraseña") },
//            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
//            trailingIcon = {
//                val icon = if (passwordVisible.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
//                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
//                    Icon(imageVector = icon, contentDescription = "Toggle password visibility")
//                }
//            },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
//            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
//            modifier = Modifier.fillMaxWidth(),
//            singleLine = true
//        )
//
//        if (loginError != null) {
//            Spacer(modifier = Modifier.height(10.dp))
//            Text(text = loginError ?: "", color = Color.Red, fontSize = 14.sp)
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Button(
//            onClick = { viewModel.login() },
//            //enabled = loginEnable,
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(10.dp),
//            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
//        ) {
//            Text("Iniciar Sesión", style = MaterialTheme.typography.labelLarge)
//        }
//    }
//}

@Composable
fun LoginScreen(
    navController: NavController,
    authVM: AuthViewModel = viewModel()
) {
    val email by authVM.email.collectAsStateWithLifecycle()
    val password by authVM.password.collectAsStateWithLifecycle()

    val navegarAlMenu by authVM.navegarAlMenu.collectAsStateWithLifecycle()

    // nos suscribimos a los stateFlows del viewModel
    val loginState by authVM.loginState.collectAsStateWithLifecycle()
    val departamentoState by authVM.departamentoState.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val passwordVisible  = remember { mutableStateOf(false) }

    // cuando departamento sea Succes navegamos.
    LaunchedEffect(navegarAlMenu) {
        if (navegarAlMenu) {
            navController.navigate(DepartamentosScreen.MenuPrincipal.name) {
                popUpTo(DepartamentosScreen.LoginApi.name) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Icon(Icons.Default.AccountCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(120.dp))
        Spacer(Modifier.height(16.dp))
        Text("Inicio de Sesión", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { authVM.email.value = it },
            label = { Text("Correo electrónico") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { authVM.password.value = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible.value) Icons.Default.VisibilityOff else Icons.Default.Visibility
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { authVM.login() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(Modifier.height(16.dp))

        // Mostramos según loginState
        when (loginState) {
            UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text((loginState as UiState.Error).message, color = Color.Red)
            else -> { /* en Success aún esperando depto */ }
        }

        // Si ocurre error cargando departamento
        if (departamentoState is UiState.Error) {
            Text((departamentoState as UiState.Error).message, color = Color.Red)
        }
    }

}
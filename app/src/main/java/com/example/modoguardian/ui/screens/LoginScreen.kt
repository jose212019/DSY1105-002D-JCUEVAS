@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.example.modoguardian.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.modoguardian.R
import com.example.modoguardian.model.LoginErrores
import com.example.modoguardian.model.LoginUiState
import com.example.modoguardian.navigation.Screen
import com.example.modoguardian.ui.theme.ModoGuardianTheme
import com.example.modoguardian.ui.utils.obtenerWindowSizeClass
import com.example.modoguardian.viewmodel.LoginViewModel
import com.example.modoguardian.viewmodel.MainViewModel


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    mainViewModel: MainViewModel
) {
    val estado by loginViewModel.estado.collectAsState()
    val windowSizeClass = obtenerWindowSizeClass()

    LoginContenido(
        estado = estado,
        widthSizeClass = windowSizeClass.widthSizeClass,
        onEmailChange = loginViewModel::onEmailChange,
        onPasswordChange = loginViewModel::onPasswordChange,
        onSimularSinConexionChange = loginViewModel::onSimularSinConexionChange,
        onIniciarSesion = {
            // Si el login es correcto, se navega al Home limpiando la pila (atrás no vuelve al login)
            loginViewModel.iniciarSesion {
                mainViewModel.navigateTo(Screen.Home, limpiarPila = true)
            }
        }
    )
}


@Composable
fun LoginContenido(
    estado: LoginUiState,
    widthSizeClass: WindowWidthSizeClass,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSimularSinConexionChange: (Boolean) -> Unit,
    onIniciarSesion: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        // Columna externa con scroll: evita que el contenido se corte en pantallas bajas (landscape)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (widthSizeClass == WindowWidthSizeClass.Compact) {
                // LAYOUT COMPACT (celular): todo en una sola Column vertical
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EncabezadoLogin(tamanoLogo = 120.dp)
                    Spacer(modifier = Modifier.height(24.dp))
                    FormularioLogin(
                        estado = estado,
                        onEmailChange = onEmailChange,
                        onPasswordChange = onPasswordChange,
                        onSimularSinConexionChange = onSimularSinConexionChange,
                        onIniciarSesion = onIniciarSesion
                    )
                }
            } else {
                // LAYOUT MEDIUM / EXPANDED (tablet, landscape): Card centrada con una Row (logo | formulario)
                Card(
                    modifier = Modifier
                        .widthIn(max = 900.dp)
                        .fillMaxWidth()
                        .padding(32.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(32.dp),
                        horizontalArrangement = Arrangement.spacedBy(32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        EncabezadoLogin(
                            tamanoLogo = 180.dp,
                            modifier = Modifier.weight(1f)
                        )
                        FormularioLogin(
                            estado = estado,
                            onEmailChange = onEmailChange,
                            onPasswordChange = onPasswordChange,
                            onSimularSinConexionChange = onSimularSinConexionChange,
                            onIniciarSesion = onIniciarSesion,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

// logo y títulos ---
@Composable
private fun EncabezadoLogin(
    tamanoLogo: Dp,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo de ModoGuardian",
            modifier = Modifier
                .size(tamanoLogo)
                .clip(RoundedCornerShape(24.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "ModoGuardian",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Inicia sesión para continuar",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

//campos, errores y botón
@Composable
private fun FormularioLogin(
    estado: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSimularSinConexionChange: (Boolean) -> Unit,
    onIniciarSesion: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Correo
        OutlinedTextField(
            value = estado.email,
            onValueChange = onEmailChange,
            label = { Text(text = "Correo electrónico") },
            singleLine = true,
            enabled = !estado.isLoading,
            isError = estado.errores.email != null,
            supportingText = {
                estado.errores.email?.let { Text(text = it) }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Contraseña
        OutlinedTextField(
            value = estado.password,
            onValueChange = onPasswordChange,
            label = { Text(text = "Contraseña") },
            singleLine = true,
            enabled = !estado.isLoading,
            isError = estado.errores.password != null,
            supportingText = {
                estado.errores.password?.let { Text(text = it) }
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Error general: credenciales incorrectas, sin conexión o error inesperado
        estado.errores.general?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Interruptor de demostración para probar el error de conectividad
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = estado.simularSinConexion,
                onCheckedChange = onSimularSinConexionChange,
                enabled = !estado.isLoading
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Simular sin conexión (demo)",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Botón: siempre habilitado a menos que esté cargando
        Button(
            onClick = onIniciarSesion,
            enabled = !estado.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            if (estado.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Ingresando...")
            } else {
                Text(text = "Iniciar sesión")
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Previews: uno por cada tamaño de ventana
// ---------------------------------------------------------------------------
@Preview(name = "Login Compact", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
private fun LoginCompactPreview() {
    ModoGuardianTheme {
        LoginContenido(
            estado = LoginUiState(),
            widthSizeClass = WindowWidthSizeClass.Compact,
            onEmailChange = {},
            onPasswordChange = {},
            onSimularSinConexionChange = {},
            onIniciarSesion = {}
        )
    }
}

@Preview(name = "Login Medium", widthDp = 700, heightDp = 900, showBackground = true)
@Composable
private fun LoginMediumPreview() {
    ModoGuardianTheme {
        LoginContenido(
            estado = LoginUiState(
                email = "correo-invalido",
                errores = LoginErrores(email = "Ingresa un correo válido")
            ),
            widthSizeClass = WindowWidthSizeClass.Medium,
            onEmailChange = {},
            onPasswordChange = {},
            onSimularSinConexionChange = {},
            onIniciarSesion = {}
        )
    }
}

@Preview(name = "Login Expanded", widthDp = 1100, heightDp = 800, showBackground = true)
@Composable
private fun LoginExpandedPreview() {
    ModoGuardianTheme {
        LoginContenido(
            estado = LoginUiState(
                email = "admin@guardian.test",
                password = "123456",
                isLoading = true
            ),
            widthSizeClass = WindowWidthSizeClass.Expanded,
            onEmailChange = {},
            onPasswordChange = {},
            onSimularSinConexionChange = {},
            onIniciarSesion = {}
        )
    }
}
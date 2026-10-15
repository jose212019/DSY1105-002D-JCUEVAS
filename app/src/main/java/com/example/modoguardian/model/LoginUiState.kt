package com.example.modoguardian.model


data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    // Interruptor de demostración para probar el manejo de error si es que no hay conexion
    val simularSinConexion: Boolean = false,
    // Rol del usuario autenticado
    val rol: Rol? = null,
    val errores: LoginErrores = LoginErrores()
) {

    val isLoginEnabled: Boolean
        get() = email.isNotBlank() && password.isNotBlank() && !isLoading
}

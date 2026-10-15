package com.example.modoguardian.repository

import com.example.modoguardian.model.ResultadoAuth
import com.example.modoguardian.model.Rol
import com.example.modoguardian.model.Usuario
import kotlinx.coroutines.delay

// usuarios ficticios
class AuthRepository {

    private val usuariosPrueba = listOf(
        Usuario(email = "admin@guardian.test", password = "123456", rol = Rol.ADMIN),
        Usuario(email = "supervisor@guardian.test", password = "123456", rol = Rol.SUPERVISOR),
        Usuario(email = "operador@guardian.test", password = "123456", rol = Rol.OPERADOR)
    )

    suspend fun autenticar(
        email: String,
        password: String,
        simularSinConexion: Boolean
    ): ResultadoAuth {

        delay(timeMillis = 1500)

        // Simula una caída de conexion
        if (simularSinConexion) return ResultadoAuth.SinConexion

        val usuario = usuariosPrueba.firstOrNull {
            it.email.equals(email.trim(), ignoreCase = true) && it.password == password
        }
        return if (usuario != null) {
            ResultadoAuth.Exito(usuario.rol)
        } else {
            ResultadoAuth.CredencialesInvalidas
        }
    }
}

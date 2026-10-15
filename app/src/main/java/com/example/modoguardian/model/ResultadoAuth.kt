package com.example.modoguardian.model


sealed class ResultadoAuth {
    data class Exito(val rol: Rol) : ResultadoAuth()
    data object CredencialesInvalidas : ResultadoAuth()
    data object SinConexion : ResultadoAuth()
    data object ErrorInesperado : ResultadoAuth()
}

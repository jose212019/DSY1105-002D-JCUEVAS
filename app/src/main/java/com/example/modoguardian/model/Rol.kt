package com.example.modoguardian.model


enum class Rol(
    val etiqueta: String,
    val descripcion: String,
    val puedeCambiarModo: Boolean,
    val modulos: List<String>
) {
    ADMIN(
        etiqueta = "Admin",
        descripcion = "Acceso total al sistema.",
        puedeCambiarModo = true,
        modulos = listOf("Gestión de usuarios", "Eventos de seguridad", "Modo Guardián")
    ),
    SUPERVISOR(
        etiqueta = "Supervisor",
        descripcion = "Supervisa eventos y controla el Modo Guardián.",
        puedeCambiarModo = true,
        modulos = listOf("Eventos de seguridad", "Modo Guardián")
    ),
    OPERADOR(
        etiqueta = "Operador",
        descripcion = "Solo consulta de eventos (solo lectura).",
        puedeCambiarModo = false,
        modulos = listOf("Eventos de seguridad (solo lectura)")
    )
}

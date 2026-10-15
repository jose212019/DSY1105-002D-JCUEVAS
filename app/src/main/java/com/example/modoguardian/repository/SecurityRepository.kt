package com.example.modoguardian.repository

import com.example.modoguardian.model.SecurityEvent

class SecurityRepository {

    // lista de eventos de seguridad iniciales
    fun getSecurityEvents(): List<SecurityEvent> {
        return listOf(
            SecurityEvent(
                id = "1",
                title = "Movimiento detectado",
                description = "Se detectó actividad en el perímetro principal."
            ),
            SecurityEvent(
                id = "2",
                title = "Estado del sistema",
                description = "Todos los módulos de vigilancia operando con normalidad."
            )
        )
    }
}
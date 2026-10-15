package com.example.modoguardian.model


data class LoginErrores(
    val email: String? = null,
    val password: String? = null,

    val general: String? = null
) {
    val hayErrores: Boolean
        get() = email != null || password != null || general != null
}

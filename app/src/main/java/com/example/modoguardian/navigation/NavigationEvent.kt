package com.example.modoguardian.navigation

// Eventos de navegación que emite MainViewModel y colecta MainActivity
sealed class NavigationEvent {
    data class NavigateTo(
        val destination: Screen,
        val limpiarPila: Boolean = false
    ) : NavigationEvent()

    data object NavigateBack : NavigationEvent()
}

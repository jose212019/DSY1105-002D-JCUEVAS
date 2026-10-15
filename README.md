# ModoGuardian

ModoGuardian es una aplicación Android de prototipo para el monitoreo de seguridad. Permite que distintos tipos de usuario inicien sesión y vean la información de seguridad según su rol, además de activar o desactivar el "Modo Guardián" del sistema.

Está hecha con Kotlin y Jetpack Compose, siguiendo la arquitectura MVVM. Usa solo datos de prueba, no tiene servidor ni credenciales reales.

## Roles

La app tiene tres tipos de usuario, y cada uno ve cosas distintas:

| Rol | Qué puede hacer |
|---|---|
| Admin | Gestión de usuarios, ver eventos y activar o desactivar el Modo Guardián |
| Supervisor | Ver eventos y activar o desactivar el Modo Guardián |
| Operador | Solo ver los eventos de seguridad (solo lectura) |

## Funcionalidades

- Inicio de sesión con validación de correo y contraseña.
- Pantalla principal distinta para cada rol.
- Lista de eventos de seguridad.
- Modo Guardián: se puede activar o desactivar y la app recuerda el estado.
- Estado de carga y mensaje de error cuando falla la conexión.
- Diseño adaptable a celular, tablet y pantalla horizontal.

## Usuarios de prueba

| Correo | Contraseña | Rol |
|---|---|---|
| admin@guardian.test | 123456 | Admin |
| supervisor@guardian.test | 123456 | Supervisor |
| operador@guardian.test | 123456 | Operador |

## Tecnologías

- Kotlin
- Jetpack Compose y Material 3
- Navigation Compose
- ViewModel con StateFlow y SharedFlow
- DataStore Preferences
- Window Size Classes

## Organización del código

```
modoguardian/
├── MainActivity.kt   (navegación)
├── data/             (guardado local con DataStore)
├── model/            (estados, roles y errores)
├── navigation/       (pantallas y eventos de navegación)
├── repository/       (usuarios y eventos de prueba)
├── viewmodel/        (lógica de cada pantalla)
└── ui/               (pantallas, componentes, tema y utilidades)
```

## Cómo ejecutar

1. Clonar el repositorio y cambiar a la rama `base-mvvm`.
2. Abrir la carpeta con Android Studio y esperar el Gradle Sync.
3. Elegir un emulador y presionar Run.
4. Iniciar sesión con uno de los usuarios de prueba.

Para ver el error de conexión, activar "Simular sin conexión (demo)" en el login e intentar entrar.

## Autores

- José Cuevas
- Salvador Briceño

package com.example.modoguardian.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.modoguardian.navigation.Screen
import com.example.modoguardian.ui.components.ImagenInteligente
import com.example.modoguardian.viewmodel.MainViewModel
import com.example.modoguardian.viewmodel.PerfilViewModel
import java.io.File

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: MainViewModel,
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val items = listOf(Screen.Home, Screen.Profile)
    var selectedItem by remember { mutableStateOf(1) }


    val context = LocalContext.current
    val imagenUri by perfilViewModel.imagenUri.collectAsState()
    var uriCamaraTemporal by remember { mutableStateOf<Uri?>(null) }

    // Lanzador para abrir la Galeria
    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) perfilViewModel.actualizarImagen(uri)
    }

    // Lanzador para abrir la Cámara
    val launcherCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito ->
        if (exito && uriCamaraTemporal != null) {
            perfilViewModel.actualizarImagen(uriCamaraTemporal)
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            viewModel.navigateTo(screen)
                        },
                        label = { Text(screen.route) },
                        icon = {
                            Icon(
                                imageVector = if (screen == Screen.Home) Icons.Default.Home else Icons.Default.Person,
                                contentDescription = screen.route
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            ImagenInteligente(imagenUri = imagenUri)

            Spacer(modifier = Modifier.height(40.dp))

            // Botón para seleccionar de la Galeria
            Button(
                onClick = { launcherGaleria.launch("image/*") },
                modifier = Modifier.fillMaxWidth().height(55.dp)
            ) {
                Text("Seleccionar de la Galería", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para tomar foto con la Cámara
            Button(
                onClick = {
                    // Crea un archivo temporal usando el FileProvider de la Parte 1
                    val archivoTemporal = File.createTempFile("foto_", ".jpg", context.cacheDir)
                    uriCamaraTemporal = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.provider",
                        archivoTemporal
                    )
                    launcherCamara.launch(uriCamaraTemporal!!)
                },
                modifier = Modifier.fillMaxWidth().height(55.dp)
            ) {
                Text("Tomar Foto con Cámara", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
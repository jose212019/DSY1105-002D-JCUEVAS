package com.example.modoguardian.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImagenInteligente(imagenUri: Uri?, modifier: Modifier = Modifier) {
    // Caja base con forma circular
    Box(
        modifier = modifier
            .size(150.dp) // Tamaño de la foto de perfil
            .clip(CircleShape) // Recorte circular
            .background(Color.LightGray), // Fondo por si no hay foto
        contentAlignment = Alignment.Center
    ) {
        if (imagenUri != null) {
            // Si hay una foto (cámara o galería), la mostramos con Coil
            AsyncImage(
                model = imagenUri,
                contentDescription = "Foto de perfil",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Ajusta la imagen al círculo
            )
        } else {
            // Si no hay foto, mostramos un icono por defecto
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Icono por defecto",
                modifier = Modifier.size(80.dp),
                tint = Color.Gray
            )
        }
    }
}
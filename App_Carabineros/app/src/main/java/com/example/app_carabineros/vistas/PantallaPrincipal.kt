package com.example.app_carabineros.vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_carabineros.model.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    usuario: Usuario,
    onCerrarSesion: () -> Unit,
    onAbrirDocumentos: () -> Unit = {},
    onAbrirTrivias: () -> Unit = {},
    onAbrirCalificaciones: () -> Unit = {},
    onAbrirAsistenteIA: () -> Unit = {}
) {
    Scaffold(
        containerColor = FondoApp,
        topBar = {
            TopAppBar(
                title = { Text("ESFOCAR Académico", color = Color.White, fontWeight = FontWeight.SemiBold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdeEsfocar),
                actions = {
                    IconButton(onClick = onCerrarSesion) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Cerrar sesión",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                Text(
                    text = "Bienvenido/a, ${usuario.nombre}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdeEsfocarOscuro
                )
                Text(
                    text = "RUT: ${usuario.rut}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }

            MenuCard(
                titulo = "Documentos Policiales",
                subtitulo = "Apuntes y normativas",
                icono = Icons.Default.Description,
                onClick = onAbrirDocumentos
            )

            MenuCard(
                titulo = "Exámenes y Trivias",
                subtitulo = "Evaluaciones y preguntas",
                icono = Icons.Default.Quiz,
                onClick = onAbrirTrivias
            )

            MenuCard(
                titulo = "Mis Calificaciones",
                subtitulo = "Notas de tus asignaturas",
                icono = Icons.Default.Grade,
                onClick = onAbrirCalificaciones
            )

            MenuCard(
                titulo = "Asistente IA",
                subtitulo = "Consulta de dudas rápidas",
                icono = Icons.Default.AutoAwesome,
                onClick = onAbrirAsistenteIA
            )
        }
    }
}

@Composable
fun MenuCard(
    titulo: String,
    subtitulo: String,
    icono: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = VerdeTarjeta)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(VerdeEsfocar),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = VerdeEsfocar
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitulo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF2E4736)
                )
            }
        }
    }
}
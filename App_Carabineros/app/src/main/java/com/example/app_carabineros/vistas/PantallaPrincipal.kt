package com.example.app_carabineros.vistas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(){
    val contexto = androidx.compose.ui.platform.LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title ={ Text("ESFOCAR Academico", color  = Color.White)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF004B23)
                ),
                actions = {
                    TextButton(onClick = {
                        val intent = android.content.Intent(contexto, LoginActivity::class.java)
                        contexto.startActivity(intent)
                        (contexto as? PrincipalActivity)?.finish()
                    }
                    ){
                        Text("Cerrar Sesion", color = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Bienvenido al sistema",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Botón 1: Módulos de estudio
            MenuCard(
                titulo = "Documentos Policiales",
                subtitulo = "Apuntes y normativas",
                onClick = { /* Próximamente: Navegar a módulos */ }
            )

            // Botón 2: Simulador
            MenuCard(
                titulo = "Exámenes y Trivias",
                subtitulo = "Evaluaciones y preguntas",
                onClick = { /* Próximamente: Navegar a trivias */ }
            )

            // Botón 3: Calificaciones
            MenuCard(
                titulo = "Mis Calificaciones",
                subtitulo = "Notas de tus asignaturas",
                onClick = { /* Próximamente: Navegar a notas */ }
            )

            // Botón 4: Asistente IA
            MenuCard(
                titulo = "Asistente IA",
                subtitulo = "Consulta de dudas rápidas",
                onClick = { /* Próximamente: Navegar a IA */ }
            )
        }
    }
}

@Composable
fun MenuCard(titulo: String, subtitulo: String, onClick: () -> Unit) {
    Card(
        onClick = onClick, // Hace que toda la tarjeta sea un botón
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
    ) {
        Column {
            Text(text = titulo, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF004B23))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitulo, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

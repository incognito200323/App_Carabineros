package com.example.app_carabineros.vistas

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_carabineros.data.DocumentoRepository
import com.example.app_carabineros.model.Documento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDocumentos(
    documentoRepository: DocumentoRepository,
    onVolver: () -> Unit
) {
    val contexto = LocalContext.current
    val scope = rememberCoroutineScope()
    val documentos by documentoRepository.obtenerDocumentos().collectAsState(initial = emptyList())

    var textoBusqueda by remember { mutableStateOf("") }
    var mostrarDialogoNuevo by remember { mutableStateOf(false) }
    var documentoEnLectura by remember { mutableStateOf<Documento?>(null) }
    var documentoAEliminar by remember { mutableStateOf<Documento?>(null) }

    val documentosFiltrados = remember(documentos, textoBusqueda) {
        if (textoBusqueda.isBlank()) {
            documentos
        } else {
            val consulta = textoBusqueda.trim().lowercase()
            documentos.filter {
                it.titulo.lowercase().contains(consulta) ||
                        it.contenido.lowercase().contains(consulta)
            }
        }
    }

    Scaffold(
        containerColor = FondoApp,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Documentos Policiales",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdeEsfocar)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarDialogoNuevo = true },
                containerColor = VerdeEsfocar,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar documento")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Barra de búsqueda
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                placeholder = { Text("Buscar normativa o apunte...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (textoBusqueda.isNotEmpty()) {
                        IconButton(onClick = { textoBusqueda = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Limpiar búsqueda")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (documentosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            tint = VerdeEsfocar.copy(alpha = 0.4f),
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (textoBusqueda.isBlank()) {
                                "Aún no tienes documentos guardados"
                            } else {
                                "No se encontraron coincidencias"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Presiona + para agregar un apunte o normativa",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 88.dp)
                ) {
                    items(documentosFiltrados, key = { it.id }) { doc ->
                        DocumentoCard(
                            documento = doc,
                            onVerDetalle = { documentoEnLectura = doc },
                            onEliminar = { documentoAEliminar = doc }
                        )
                    }
                }
            }
        }
    }

    // Diálogo para agregar un nuevo documento
    if (mostrarDialogoNuevo) {
        DialogoNuevoDocumento(
            onDismiss = { mostrarDialogoNuevo = false },
            onGuardar = { titulo, contenido, uriArchivo ->
                val fechaHoy = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                val nuevoDoc = Documento(
                    titulo = titulo,
                    contenido = contenido,
                    fechaCreacion = fechaHoy,
                    uriArchivo = uriArchivo
                )
                scope.launch {
                    withContext(Dispatchers.IO) {
                        documentoRepository.agregarDocumento(nuevoDoc)
                    }
                    mostrarDialogoNuevo = false
                    Toast.makeText(contexto, "Documento guardado", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    // Diálogo para leer el contenido completo y abrir adjunto
    documentoEnLectura?.let { doc ->
        AlertDialog(
            onDismissRequest = { documentoEnLectura = null },
            title = {
                Text(
                    text = doc.titulo,
                    fontWeight = FontWeight.Bold,
                    color = VerdeEsfocar
                )
            },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = "Creado el ${doc.fechaCreacion}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = doc.contenido,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF1E2922)
                    )

                    if (!doc.uriArchivo.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW).apply {
                                        data = Uri.parse(doc.uriArchivo)
                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    }
                                    contexto.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        contexto,
                                        "No se encontró una app para abrir este archivo",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.OpenInNew, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Abrir archivo adjunto")
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { documentoEnLectura = null }) {
                    Text("Cerrar", color = VerdeEsfocar)
                }
            }
        )
    }

    // Diálogo de confirmación para eliminar
    documentoAEliminar?.let { doc ->
        AlertDialog(
            onDismissRequest = { documentoAEliminar = null },
            title = { Text("Eliminar documento") },
            text = { Text("¿Seguro que deseas eliminar \"${doc.titulo}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                documentoRepository.eliminarDocumento(doc)
                            }
                            documentoAEliminar = null
                            Toast.makeText(contexto, "Documento eliminado", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { documentoAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun DocumentoCard(
    documento: Documento,
    onVerDetalle: () -> Unit,
    onEliminar: () -> Unit
) {
    ElevatedCard(
        onClick = onVerDetalle,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(VerdeTarjeta),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null,
                    tint = VerdeEsfocar,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = documento.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = VerdeEsfocarOscuro,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = documento.contenido,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = documento.fechaCreacion,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    if (!documento.uriArchivo.isNullOrBlank()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AttachFile,
                                contentDescription = "Archivo adjunto",
                                tint = VerdeEsfocar,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Adjunto",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = VerdeEsfocar
                            )
                        }
                    }
                }
            }

            IconButton(onClick = onEliminar) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun DialogoNuevoDocumento(
    onDismiss: () -> Unit,
    onGuardar: (titulo: String, contenido: String, uriArchivo: String?) -> Unit
) {
    val contexto = LocalContext.current
    var titulo by remember { mutableStateOf("") }
    var contenido by remember { mutableStateOf("") }
    var uriArchivo by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf("") }

    val selectorArchivo = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                contexto.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: SecurityException) {
                // Algunos proveedores no requieren permiso persistente
            }
            uriArchivo = uri.toString()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Nuevo Documento",
                fontWeight = FontWeight.Bold,
                color = VerdeEsfocar
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it; error = "" },
                    label = { Text("Título o normativa") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = contenido,
                    onValueChange = { contenido = it; error = "" },
                    label = { Text("Resumen o contenido") },
                    minLines = 3,
                    maxLines = 5,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedButton(
                    onClick = { selectorArchivo.launch(arrayOf("application/pdf", "image/*")) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.AttachFile, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (uriArchivo == null) {
                            "Adjuntar PDF o imagen (opcional)"
                        } else {
                            "Archivo adjuntado (cambiar)"
                        }
                    )
                }

                if (error.isNotEmpty()) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (titulo.isBlank() || contenido.isBlank()) {
                        error = "Completa el título y el contenido."
                    } else {
                        onGuardar(titulo.trim(), contenido.trim(), uriArchivo)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = VerdeEsfocar)
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
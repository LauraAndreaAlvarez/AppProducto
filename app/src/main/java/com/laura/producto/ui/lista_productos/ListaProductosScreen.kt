package com.laura.producto.ui.lista_productos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ListaProductosScreen(viewModel: ListaProductosViewModel = hiltViewModel()) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { viewModel.cargarProductos() }

    Column(Modifier.padding(16.dp)) {
        // Formulario para añadir
        Text("Nuevo Producto", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") })
        OutlinedTextField(value = categoria, onValueChange = { categoria = it }, label = { Text("Categoría") })

        // Calcula si los datos son válidos
        val esValido = nombre.trim().length >= 3 &&
                nombre.trim().length <= 50
                (precio.toDoubleOrNull() ?: 0.0) > 0.0 &&
                categoria.isNotBlank()

        Button(onClick = {
            viewModel.agregarProducto(nombre, precio.toDoubleOrNull() ?: 0.0, categoria)
            nombre = ""; precio = ""; categoria = ""
            },
            enabled = esValido // el botón solo se puede pulsar si es válido
            ) {
            Text("Añadir Producto")
        }

        HorizontalDivider(Modifier.padding(vertical = 16.dp))

        var textoBusqueda by remember { mutableStateOf("") }

        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = {
                textoBusqueda = it
                viewModel.buscarProducto(it) // Búsqueda en tiempo real
            },
            label = { Text("Buscar por nombre...") },
            modifier = Modifier.fillMaxWidth()
        )

        // Lista de productos
        if (viewModel.cargando) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(viewModel.productos) { producto ->
                    Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        ListItem(
                            headlineContent = { Text(producto.nombre) },
                            supportingContent = { Text("${producto.categoria} - ${producto.precio}€") }
                        )
                    }
                }
            }
        }
    }
}
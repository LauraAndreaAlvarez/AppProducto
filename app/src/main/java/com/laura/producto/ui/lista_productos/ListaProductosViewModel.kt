package com.laura.producto.ui.lista_productos
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laura.producto.domain.model.Producto
import com.laura.producto.domain.user_case.AniadirProductoUseCase
import com.laura.producto.domain.user_case.BuscarProductosPorPatronSobreNombreUseCase
import com.laura.producto.domain.user_case.ObtenerProductosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onSuccess

// sin esto, la función hiltViewModel() en tu Compose no sabe que debe buscar
// en el sistema de inyección de dependencias
@HiltViewModel  // indica a Hilt que este es un ViewModel inyectable
class ListaProductosViewModel @Inject constructor(  // indica que debe inyectar estos casos de uso
    // @Inject constructor: Esto le dice a Hilt: "Busca en el NetworkModule cómo se crean
    // ObtenerProductosUseCase y AniadirProductoUseCase y pásamelos aquí".
    private val obtenerProductosUseCase: ObtenerProductosUseCase,
    private val aniadirProductoUseCase: AniadirProductoUseCase,
    private val buscarProductosPorPatronUseCase: BuscarProductosPorPatronSobreNombreUseCase
) : ViewModel() {

    // Estado para la lista de productos
    var productos by mutableStateOf<List<Producto>>(emptyList())
        private set
    // Estado para controlar si está cargando
    var cargando by mutableStateOf(false)
        private set
    // Estado para mensajes de error o éxito
    var mensajeError by mutableStateOf<String?>(null)
        private set

    // Cargar productos al iniciar
    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            cargando = true
            mensajeError = null
            try {
                productos = obtenerProductosUseCase()
            } catch (e: Exception) {
                mensajeError = "Error al cargar productos"
            } finally {
                cargando = false
            }
        }
    }
    fun buscarProducto(query: String) {
        if (query.isEmpty() || query.isBlank()) {
            cargarProductos()
            return
        }
        viewModelScope.launch {
            try {
                val resultados = buscarProductosPorPatronUseCase(query)
                productos = resultados // se actualiza la lista con los filtros
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error en búsqueda", e)
            }
        }
    }

    fun agregarProducto(nombre: String, precio: Double, categoria: String) {
        Log.d("DEBUG_APP", "Intentando guardar: $nombre, $precio") // <-- Mira si esto sale
        viewModelScope.launch {
            cargando = true
            val nuevo = Producto(id = null, nombre = nombre, precio = precio, categoria = categoria)
            // api.crear(nuevo)
            val resultado = aniadirProductoUseCase(nuevo)
            resultado.onSuccess {
                mensajeError = null
                cargarProductos()  // se refresca la lista tras el éxito
            }.onFailure { error ->
                mensajeError = "Error al insertar: ${error.message}"
                Log.e("API_ERROR", "Error al insertar", error) // Mira esto en el Logcat
            }
            cargando = false
        }
    }

    fun guardar(nombre: String, precio: Double, categoria: String) {
        Log.d("DEBUG_APP", "Intentando guardar: $nombre, $precio") // <-- Mira si esto sale
        viewModelScope.launch {
            cargando = true
            val nuevoProducto = Producto(null, nombre, precio, categoria)

            val resultado = aniadirProductoUseCase(nuevoProducto)

            resultado.onSuccess {
                mensajeError = null
                cargarProductos()  // se refresca la lista tras el éxito
            }.onFailure { error ->
                mensajeError = error.message
                Log.e("API_ERROR", "Error al insertar", error) // Mira esto en el Logcat
            }
            cargando = false
        }
    }
    // Función para limpiar el error después de mostrarlo (ej. en un Snackbar)
    fun errorMostrado() {
        mensajeError = null
    }
}
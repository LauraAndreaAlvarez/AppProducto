package com.laura.producto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.laura.producto.ui.lista_productos.ListaProductosViewModel
import com.laura.producto.ui.lista_productos.ListaProductosScreen
import com.laura.producto.ui.theme.ProductoTheme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // ESTA LÍNEA HACE TODO EL TRABAJO:
            // Sustituye a:
            //    val api = RetrofitClient.instance
            //    val repo = ProductoRepositoryImpl(api)
            //    val useCase = GetProductosUseCase(repo)
            // El ViewModel ahora recibe el 'useCase' por constructor
            // Hilt detecta que ProductoViewModel necesita UseCases,
            // los busca en sus módulos y te entrega el ViewModel listo para usar.

            ProductoTheme() {
                // Surface es el contenedor base que usa el color de fondo del tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // hiltViewModel() obtiene automáticamente la instancia inyectada
                    val viewModel: ListaProductosViewModel = hiltViewModel()

                    // Llamamos a la pantalla principal que creamos antes
                    ListaProductosScreen(viewModel = viewModel)
                }
            }
        }
    }
}
package com.laura.producto.domain.user_case

import android.util.Log
import javax.inject.Inject
import com.laura.producto.domain.model.Producto
import com.laura.producto.domain.repository.ProductoRepository

class AniadirProductoUseCase @Inject constructor(private val productoRepositorio: ProductoRepository) {
    suspend operator fun invoke(producto: Producto): Result<Producto> {
        // aquí podrías añadir lógica de negocio (reglas) antes de guardar
        if (producto.nombre.isBlank()) {
            Log.e("ERROR_LOGICA", "El nombre del producto está vacío")
            return Result.failure(Exception("El nombre no puede estar vacío"))
        }
        if (producto.precio <= 0) {
            Log.e("ERROR_LOGICA", "El precio del producto es menor o igual que 0")
            return Result.failure(Exception("El precio debe ser positivo"))
        }

        return try {
            val resultado = productoRepositorio.insertProducto(producto)
            Result.success(resultado)
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error al insertar: ${e.message}")
            Result.failure(e)
        }
    }
}
package com.laura.producto.domain.repository

import com.laura.producto.domain.model.Producto
/*
  El Repository maneja ambos, pero depende de hacia dónde mire.
  Para que tu arquitectura sea limpia y funcione bien en Android,
   la regla de oro es:
     - Hacia afuera (ViewModel/UseCases): El Repository siempre debe devolver
       y recibir Producto (el objeto de dominio).
     - Hacia adentro (API/Retrofit): El Repository usa ProductoDto para hablar
       con el servidor.🔄
    ¿Cómo fluyen los datos en el Repository?
    1. Cuando pides datos (GET)
    El flujo es de API hacia UI:
        1. El Repository recibe un ProductoDto
     desde Retrofit.
        2. El Repository lo mapea a Producto.
        3. El ViewModel recibe un Producto.
    2. Cuando envías datos (POST/PUT)
        1. El flujo es de UI hacia API: El ViewModel le pasa un Producto al Repository.
        2. El Repository lo mapea a ProductoDto.
        3. El Repository envía el ProductoDto a Retrofit.
 */
interface ProductoRepository {
    // Métodos de acceso a la BD local con Room


    // Métodos de acceso a la API remota con Retrofit

    // Obtener todos los productos
    suspend fun getProductos(): List<Producto>

    // Insertar un producto
    // devuelve el producto con el ID asignado por la API
    suspend fun insertProducto(producto: Producto): Producto

    // Buscar por nombre o patrón
    suspend fun getProductosPorPatron(patron: String): List<Producto>

    // Obtener uno solo por su ID
    suspend fun getProductoById(id: Long): Producto?

    // Actualizar un producto existente
    suspend fun updateProducto(id: Long, producto: Producto): Producto

    // Eliminar un producto
    // devolvemos un Boolean para confirmar si la operación fue exitosa.
    suspend fun deleteProducto(id: Long): Boolean
}
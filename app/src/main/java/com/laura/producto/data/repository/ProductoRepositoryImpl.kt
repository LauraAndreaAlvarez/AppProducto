package com.laura.producto.data.repository

import javax.inject.Inject
import com.laura.producto.data.remote.ProductoApiService
import com.laura.producto.data.remote.mapper.toDomain
import com.laura.producto.data.remote.mapper.toDomainList
import com.laura.producto.data.remote.mapper.toDto
import com.laura.producto.domain.model.Producto
import com.laura.producto.domain.repository.ProductoRepository

class ProductoRepositoryImpl @Inject constructor(
    private val apiProducto: ProductoApiService  // la interfaz de Retrofit
) : ProductoRepository  {

    // Métodos de acceso a la BD local con Room


    // Métodos de acceso a la API remota con Retrofit
    // override suspend fun getProductos(): List<Producto> = api.listarTodos()
    // override suspend fun insertProducto(producto: Producto) = api.crear(producto)
    // override suspend fun getProductosPorPatron(patron: String): List<Producto> = api.buscarPorNombre(patron)

    /*
    override suspend fun insertarProducto(producto: Producto): Producto {
        // Convertimos a DTO para enviarlo a la API
        val dtoParaEnviar = producto.toDto()

        // Llamada a Retrofit
        val respuestaDto = api.crear(dtoParaEnviar)

        // Convertimos la respuesta de la API de vuelta a nuestro modelo de dominio
        return respuestaDto.toDomain()
    }

    override suspend fun getProductos(): List<Producto> {
        return api.obtenerProductos().toDomainList()
    }
    */
    override suspend fun getProductos(): List<Producto> {
        // Llamamos a la API y convertimos la lista de DTOs a Dominio
        return apiProducto.listarTodos().toDomainList()
    }

    override suspend fun insertProducto(producto: Producto): Producto {
        // 1. Convertimos el Producto de dominio a DTO
        val dtoParaEnviar = producto.toDto()

        // 2. Enviamos el DTO a la API
        val respuestaDto = apiProducto.crear(dtoParaEnviar)

        // 3. Devolvemos el resultado convertido a dominio (ya con su ID real)
        return respuestaDto.toDomain()
    }

    override suspend fun getProductosPorPatron(patron: String): List<Producto> {
        return apiProducto.buscarPorNombre(patron).toDomainList()
    }

    override suspend fun getProductoById(id: Long): Producto? {
        return try {
            apiProducto.obtenerPorId(id).toDomain()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateProducto(id: Long, producto: Producto): Producto {
        val dto = producto.toDto()
        return apiProducto.actualizar(id, dto).toDomain()
    }

    override suspend fun deleteProducto(id: Long): Boolean {
        val respuesta = apiProducto.eliminar(id)
        return respuesta.isSuccessful
    }
}
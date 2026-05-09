package com.laura.producto.data.remote

import kotlinx.coroutines.flow.Flow
import com.laura.producto.data.remote.dto.ProductoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductoApiService {
    @GET("api/productos")
    suspend fun listarTodos(): List<ProductoDto>

    @GET("api/productos/{id}")
    suspend fun obtenerPorId(@Path("id") id: Long): ProductoDto


    @GET("api/productos")
    fun listarTodosConFlujo(): Flow<List<ProductoDto>>

    @GET("api/productos/buscar")
    suspend fun buscarPorNombre(
        @Query("nombre") nombre: String
    ): List<ProductoDto>

    @Headers("Content-Type: application/json")
    @POST("api/productos")
    suspend fun crear(@Body producto: ProductoDto): ProductoDto


    // @PUT("api/productos")
    // suspend fun modificar(@Body producto: ProductoDto): ProductoDto

    @PUT("api/productos/{id}")
    suspend fun actualizar(@Path("id") id: Long, @Body producto: ProductoDto): ProductoDto

    // Nota: Para buscar por nombre, tu API de Spring debería tener un endpoint como:
    // @GetMapping("/buscar") public List<Producto> buscar(@RequestParam String nombre)


    @DELETE("api/productos/{id}")
    suspend fun eliminar(@Path("id") id: Long): Response<Unit>

    @DELETE("api/productos")
    suspend fun eliminarTodos(): Response<Unit>
}
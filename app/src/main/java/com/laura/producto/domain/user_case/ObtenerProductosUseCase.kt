package com.laura.producto.domain.user_case

import javax.inject.Inject
import com.laura.producto.domain.model.Producto
import com.laura.producto.domain.repository.ProductoRepository


class ObtenerProductosUseCase @Inject constructor(private val productoRepositorio: ProductoRepository) {
    suspend operator fun invoke(): List<Producto> = productoRepositorio.getProductos()
}
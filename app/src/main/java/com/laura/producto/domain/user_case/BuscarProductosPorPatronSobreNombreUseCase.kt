package com.laura.producto.domain.user_case

import javax.inject.Inject
import com.laura.producto.domain.model.Producto
import com.laura.producto.domain.repository.ProductoRepository


class BuscarProductosPorPatronSobreNombreUseCase @Inject constructor(private val productoRepositorio: ProductoRepository) {
    suspend operator fun invoke(patron: String): List<Producto> = productoRepositorio.getProductosPorPatron(patron)
}
package com.laura.producto.domain.model

data class Producto(
    val id: Long? = null,
    val nombre: String,
    val precio: Double,
    val categoria: String
)
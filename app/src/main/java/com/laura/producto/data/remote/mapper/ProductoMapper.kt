package com.laura.producto.data.remote.mapper
import com.laura.producto.data.remote.dto.ProductoDto
import com.laura.producto.domain.model.Producto

// De Dominio (Producto) a API (ProductoDto)
fun Producto.toDto(): ProductoDto {
    return ProductoDto(
        id = this.id, // El id puede ser null si es un registro nuevo
        nombre = this.nombre,
        precio = this.precio,
        categoria = this.categoria
    )
}

// De API (ProductoDto) a Dominio (Producto)
fun ProductoDto.toDomain(): Producto {
    return Producto(
        id = this.id,
        nombre = this.nombre,
        precio = this.precio,
        categoria = this.categoria
    )
}

// Para listas (útil en el GET)
fun List<ProductoDto>.toDomainList(): List<Producto> {
    return this.map { it.toDomain() }
}
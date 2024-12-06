package ec.epn.edu.Model

import java.util.*

data class Reparacion(
    val id: Int,
    val descripcion: String,
    val costo: Double,
    val fecha: Date,
    val mecanico: String,
    val vehiculo: Vehiculo // Lista de objetos Vehiculo
){
    constructor(descripcion: String, costo: Double, fecha: Date, mecanico: String, vehiculo: Vehiculo)
            : this(0, descripcion, costo, fecha, mecanico, vehiculo)
}
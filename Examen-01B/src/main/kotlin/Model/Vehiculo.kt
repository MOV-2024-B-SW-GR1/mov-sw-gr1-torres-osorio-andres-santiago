package ec.epn.edu.Model

data class Vehiculo(
    val id: Int,
    val marca: String,
    val modelo: String,
    val año: Int,
    val color: String
) {
    constructor(marca: String, modelo: String, año: Int, color: String) : this(0, marca, modelo, año, color)
}

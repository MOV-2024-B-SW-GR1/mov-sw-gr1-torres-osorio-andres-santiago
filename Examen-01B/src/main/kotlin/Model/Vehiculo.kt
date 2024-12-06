package ec.epn.edu.Model

data class Vehiculo(
    val id: Int,         // Identificador único
    val marca: String,   // Marca del vehículo
    val modelo: String,  // Modelo del vehículo
    val año: Int,        // Año de fabricación
    val color: String    // Color del vehículo
) {
    // Constructor secundario sin el id, se asigna un valor por defecto
    constructor(marca: String, modelo: String, año: Int, color: String) : this(0, marca, modelo, año, color)
}

package ec.epn.edu

import ec.epn.edu.Controller.ReparacionController

fun main() {
    val controlador = ReparacionController()

    while (true) {
        println("Seleccione una opción:")
        println("1. Crear Vehículo")
        println("2. Crear Reparación")
        println("3. Ver Vehículos")
        println("4. Ver Reparaciones")
        println("5. Actualizar Vehículo")
        println("6. Actualizar Reparación")
        println("7. Eliminar Vehículo")
        println("8. Eliminar Reparación")
        println("9. Salir")

        val opcion = readLine()!!.toInt()

        when (opcion) {
            1 -> controlador.crearVehiculo()
            2 -> controlador.crearReparacion()
            3 -> controlador.verVehiculos()
            4 -> controlador.verReparaciones()
            5 -> controlador.actualizarVehiculo()
            6 -> controlador.actualizarReparacion()
            7 -> controlador.eliminarVehiculo()
            8 -> controlador.eliminarReparacion()
            9 -> {
                println("Saliendo...")
                break
            }
            else -> println("Opción no válida, por favor ingrese una opción correcta.")
        }
    }

}





package ec.epn.edu.Controller

import ec.epn.edu.Model.Vehiculo
import ec.epn.edu.Service.ReparacionService
import ec.epn.edu.Service.VehiculoService
import java.util.*

class ReparacionController(
    private val vehiculoService: VehiculoService = VehiculoService(),
    private val reparacionService: ReparacionService = ReparacionService()
) {

    fun crearVehiculo() {
        println("Ingrese los datos del vehículo:")
        print("Marca: ")
        val marca = readLine()!!
        print("Modelo: ")
        val modelo = readLine()!!
        print("Año: ")
        val año = readLine()!!.toInt()
        print("Color: ")
        val color = readLine()!!

        vehiculoService.createVehiculo(marca, modelo, año, color)
        println("Vehículo creado exitosamente.")
    }

    fun crearReparacion() {
        println("Ingrese los datos de la reparación:")
        print("Descripción: ")
        val descripcion = readLine()!!
        print("Costo: ")
        val costo = readLine()!!.toDouble()
        print("Mecánico: ")
        val mecanico = readLine()!!
        val fecha = Date()

        val vehiculos = vehiculoService.getAllVehiculos()
        if (vehiculos.isEmpty()) {
            println("No hay vehículos disponibles. Cree un vehículo primero.")
            return
        }

        println("Seleccione el vehículo involucrado en la reparación:")
        vehiculos.forEach { println("${it.id} - ${it.marca} ${it.modelo}") }
        val vehiculoId = readLine()!!.toInt()
        val vehiculo = vehiculoService.getVehiculoById(vehiculoId)

        if (vehiculo != null) {
            reparacionService.createReparacion(descripcion, costo, mecanico, vehiculo, fecha)
            println("Reparación creada exitosamente.")
        } else {
            println("Vehículo no encontrado.")
        }
    }


    fun verVehiculos() {
        val vehiculos = vehiculoService.getAllVehiculos()
        if (vehiculos.isEmpty()) {
            println("⚠️ No hay vehículos registrados.")
        } else {
            println("🚗 ${"Lista de vehículos".padEnd(30, ' ')} 🚗")
            println("──────────────────────────────────────────────────")
            vehiculos.forEach { vehiculo ->
                println("ID: ${vehiculo.id}".padEnd(20) + "Marca: ${vehiculo.marca}".padEnd(20) + "Modelo: ${vehiculo.modelo}" + " Año: ${vehiculo.año} " + "Color: ${vehiculo.color}")
            }
            println("──────────────────────────────────────────────────")
        }
    }

    fun verReparaciones() {
        val reparaciones = reparacionService.getAllReparaciones()
        if (reparaciones.isEmpty()) {
            println("⚠️ No hay reparaciones registradas.")
        } else {
            println("🔧 ${"Lista de reparaciones".padEnd(30, ' ')} 🔧")
            println("──────────────────────────────────────────────────")
            reparaciones.forEach { reparacion ->
                println("ID: ${reparacion.id}".padEnd(20) + "Descripción: ${reparacion.descripcion}".padEnd(40) + "Mecánico: ${reparacion.mecanico}" + "Vehiculo: ${reparacion.vehiculo.marca}  ${reparacion.vehiculo.modelo}" )
            }
            println("──────────────────────────────────────────────────")
        }
    }


    fun actualizarVehiculo() {
        println("\n===================================")
        println("  Listado de vehículos disponibles:")
        println("===================================")
        val vehiculos = vehiculoService.getAllVehiculos()

        if (vehiculos.isNotEmpty()) {
            vehiculos.forEach { vehiculo ->
                println("ID: ${vehiculo.id} | Marca: ${vehiculo.marca} | Modelo: ${vehiculo.modelo} | Año: ${vehiculo.año} | Color: ${vehiculo.color}")
                println("-----------------------------------")
            }

            println("\nIngrese el ID del vehículo a actualizar:")
            val id = readLine()!!.toInt()
            val vehiculo = vehiculoService.getVehiculoById(id)

            if (vehiculo != null) {
                println("\nIngrese los nuevos datos del vehículo (deje vacío para mantener el actual):")

                print("Marca (${vehiculo.marca}): ")
                val marca = readLine()!!.ifEmpty { vehiculo.marca }

                print("Modelo (${vehiculo.modelo}): ")
                val modelo = readLine()!!.ifEmpty { vehiculo.modelo }

                print("Año (${vehiculo.año}): ")
                val año = readLine()!!.ifEmpty { vehiculo.año.toString() }.toInt()

                print("Color (${vehiculo.color}): ")
                val color = readLine()!!.ifEmpty { vehiculo.color }

                vehiculoService.updateVehiculo(Vehiculo(id, marca, modelo, año, color))
                println("\n¡Vehículo actualizado exitosamente!")
            } else {
                println("\n🚫 Vehículo no encontrado.")
            }
        } else {
            println("\n🚫 No hay vehículos disponibles.")
        }
    }


    fun actualizarReparacion() {
        println("\n===========================================")
        println("  Listado de reparaciones disponibles:")
        println("===========================================")
        val reparaciones = reparacionService.getAllReparaciones()

        if (reparaciones.isNotEmpty()) {
            reparaciones.forEach { reparacion ->
                println("ID: ${reparacion.id} | Descripción: ${reparacion.descripcion} | Costo: ${reparacion.costo} | Mecánico: ${reparacion.mecanico}")
                val vehiculo = reparacion.vehiculo
                println("  Vehículo asociado: ID: ${vehiculo.id} | Marca: ${vehiculo.marca} | Modelo: ${vehiculo.modelo}")
                println("-------------------------------------------")
            }

            println("\nIngrese el ID de la reparación a actualizar:")
            val id = readLine()!!.toInt()
            val reparacion = reparacionService.getReparacionById(id)

            if (reparacion != null) {
                println("\nIngrese los nuevos datos de la reparación (deje vacío para mantener el actual):")

                print("Descripción (${reparacion.descripcion}): ")
                val descripcion = readLine()!!.ifEmpty { reparacion.descripcion }

                print("Costo (${reparacion.costo}): ")
                val costo = readLine()!!.ifEmpty { reparacion.costo.toString() }.toDouble()

                print("Mecánico (${reparacion.mecanico}): ")
                val mecanico = readLine()!!.ifEmpty { reparacion.mecanico }

                println("\nVehículo actualmente asociado a la reparación:")
                val vehiculoActual = reparacion.vehiculo
                println("ID: ${vehiculoActual.id} | Marca: ${vehiculoActual.marca} | Modelo: ${vehiculoActual.modelo}")

                val vehiculosDisponibles = vehiculoService.getAllVehiculos()
                println("\nVehículos disponibles para asociar a la reparación:")
                vehiculosDisponibles.forEach { vehiculo ->
                    if (reparacion.vehiculo.id != vehiculo.id) {
                        println("ID: ${vehiculo.id} | Marca: ${vehiculo.marca} | Modelo: ${vehiculo.modelo}")
                    }
                }

                println("\n¿Desea cambiar el vehículo asociado a esta reparación? (cambiar/no):")
                val accion = readLine()!!

                when (accion.lowercase()) {
                    "cambiar" -> {
                        print("\nIngrese el ID del nuevo vehículo a asociar: ")
                        val vehiculoId = readLine()?.toIntOrNull()
                        val vehiculoSeleccionado = vehiculosDisponibles.firstOrNull { it.id == vehiculoId }
                        if (vehiculoSeleccionado != null) {
                            val reparacionActualizada = reparacion.copy(
                                descripcion = descripcion,
                                costo = costo,
                                mecanico = mecanico,
                                vehiculo = vehiculoSeleccionado
                            )
                            reparacionService.updateReparacion(reparacionActualizada)
                            println("\n¡Reparación actualizada exitosamente con el nuevo vehículo!")
                        } else {
                            println("\n🚫 Vehículo no encontrado.")
                        }
                    }
                    "no" -> {
                        val reparacionActualizada = reparacion.copy(
                            descripcion = descripcion,
                            costo = costo,
                            mecanico = mecanico
                        )
                        reparacionService.updateReparacion(reparacionActualizada)
                        println("\n¡Reparación actualizada exitosamente sin cambios en el vehículo!")
                    }
                    else -> println("\n🚫 Acción no válida.")
                }
            } else {
                println("\n🚫 Reparación no encontrada.")
            }
        } else {
            println("\n🚫 No hay reparaciones disponibles.")
        }
    }






    fun eliminarVehiculo() {
        val vehiculos = vehiculoService.getAllVehiculos()
        if (vehiculos.isEmpty()) {
            println("⚠️ No hay vehículos registrados para eliminar.")
            return
        }

        println("🚗 ${"Lista de vehículos registrados".padEnd(30, ' ')} 🚗")
        println("──────────────────────────────────────────────────")
        vehiculos.forEach { vehiculo ->
            println("ID: ${vehiculo.id}".padEnd(20) + "Marca: ${vehiculo.marca}".padEnd(20) + "Modelo: ${vehiculo.modelo}")
        }
        println("──────────────────────────────────────────────────")

        println("Ingrese el ID del vehículo a eliminar:")
        val id = readLine()!!.toInt()

        val vehiculoEliminado = vehiculos.firstOrNull { it.id == id }
        if (vehiculoEliminado != null) {
            vehiculoService.deleteVehiculo(id)
            println("✅ Vehículo eliminado exitosamente.")
        } else {
            println("❌ Vehículo no encontrado con el ID $id.")
        }
    }

    fun eliminarReparacion() {
        val reparaciones = reparacionService.getAllReparaciones()
        if (reparaciones.isEmpty()) {
            println("⚠️ No hay reparaciones registradas para eliminar.")
            return
        }

        println("🔧 ${"Lista de reparaciones registradas".padEnd(30, ' ')} 🔧")
        println("──────────────────────────────────────────────────")
        reparaciones.forEach { reparacion ->
            println("ID: ${reparacion.id}".padEnd(20) + "Descripción: ${reparacion.descripcion}".padEnd(40) + "Mecánico: ${reparacion.mecanico}")
        }
        println("──────────────────────────────────────────────────")

        println("Ingrese el ID de la reparación a eliminar:")
        val id = readLine()!!.toInt()

        val reparacionEliminada = reparaciones.firstOrNull { it.id == id }
        if (reparacionEliminada != null) {
            reparacionService.deleteReparacion(id)
            println("✅ Reparación eliminada exitosamente.")
        } else {
            println("❌ Reparación no encontrada con el ID $id.")
        }
    }

}

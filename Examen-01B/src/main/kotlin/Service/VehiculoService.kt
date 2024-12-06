package ec.epn.edu.Service

import ec.epn.edu.Model.Vehiculo
import ec.epn.edu.Repository.ReparacionRepository
import ec.epn.edu.Repository.VehiculoRepository

class VehiculoService(
    private val vehiculoRepository: VehiculoRepository = VehiculoRepository(),
    private val reparacionRepository: ReparacionRepository = ReparacionRepository()
) {

    fun createVehiculo(marca: String, modelo: String, año: Int, color: String): Vehiculo {
        val vehiculo = Vehiculo(vehiculoRepository.generateId(), marca, modelo, año, color)
        vehiculoRepository.save(vehiculo)
        return vehiculo
    }

    fun getAllVehiculos(): List<Vehiculo> = vehiculoRepository.getAll()

    fun getVehiculoById(id: Int): Vehiculo? = vehiculoRepository.getById(id)

    fun deleteVehiculo(id: Int) {
        vehiculoRepository.delete(id)

        // Eliminar el vehículo de las reparaciones relacionadas
        val reparacionesRelacionadas = reparacionRepository.getAll().filter { reparacion ->
            reparacion.vehiculo?.id == id // Ahora se compara con el único vehículo
        }

        reparacionesRelacionadas.forEach { reparacion ->
            // Si la reparación solo tiene este vehículo, se elimina la reparación
            if (reparacion.vehiculo?.id == id) {
                reparacionRepository.delete(reparacion.id)
            } else {
                // Si la reparación tiene otro vehículo, no se hace nada ya que solo hay un vehículo por reparación
                println("Reparación con ID ${reparacion.id} ya no tiene este vehículo.")
            }
        }
    }

    fun updateVehiculo(vehiculoActualizado: Vehiculo) {
        // Actualizar el vehículo en la lista de vehículos
        vehiculoRepository.update(vehiculoActualizado)

        // Actualizar las reparaciones que contienen este vehículo
        val reparacionesRelacionadas = reparacionRepository.getAll().filter { reparacion ->
            reparacion.vehiculo?.id == vehiculoActualizado.id // Se busca el vehículo que se está actualizando
        }

        reparacionesRelacionadas.forEach { reparacion ->
            // Actualizar el vehículo en la reparación
            val reparacionActualizada = reparacion.copy(vehiculo = vehiculoActualizado)
            reparacionRepository.update(reparacionActualizada)
        }
    }


}

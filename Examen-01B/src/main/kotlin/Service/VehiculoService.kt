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

        val reparacionesRelacionadas = reparacionRepository.getAll().filter { reparacion ->
            reparacion.vehiculo?.id == id
        }

        reparacionesRelacionadas.forEach { reparacion ->
            if (reparacion.vehiculo?.id == id) {
                reparacionRepository.delete(reparacion.id)
            } else {
                println("Reparación con ID ${reparacion.id} ya no tiene este vehículo.")
            }
        }
    }

    fun updateVehiculo(vehiculoActualizado: Vehiculo) {

        vehiculoRepository.update(vehiculoActualizado)


        val reparacionesRelacionadas = reparacionRepository.getAll().filter { reparacion ->
            reparacion.vehiculo?.id == vehiculoActualizado.id
        }

        reparacionesRelacionadas.forEach { reparacion ->

            val reparacionActualizada = reparacion.copy(vehiculo = vehiculoActualizado)
            reparacionRepository.update(reparacionActualizada)
        }
    }


}

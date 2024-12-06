package ec.epn.edu.Service

import ec.epn.edu.Model.Reparacion
import ec.epn.edu.Model.Vehiculo
import ec.epn.edu.Repository.ReparacionRepository
import java.util.Date

class ReparacionService(private val reparacionRepository: ReparacionRepository = ReparacionRepository()) {

    // Crear una nueva reparación
    fun createReparacion(descripcion: String, costo: Double, mecanico: String, vehiculo: Vehiculo, fecha: Date): Reparacion {
        // Generamos un ID único
        val reparacion = Reparacion(
            id = reparacionRepository.generateId(), // Este método debe generar un ID único
            descripcion = descripcion,
            costo = costo,
            fecha = fecha,
            mecanico = mecanico,
            vehiculo = vehiculo // Cambiamos de lista a un solo objeto Vehiculo
        )
        reparacionRepository.save(reparacion) // Guardamos la reparación en el repositorio
        return reparacion
    }

    // Obtener todas las reparaciones
    fun getAllReparaciones(): List<Reparacion> = reparacionRepository.getAll()

    // Obtener una reparación por ID
    fun getReparacionById(id: Int): Reparacion? = reparacionRepository.getById(id)

    // Actualizar una reparación existente
    fun updateReparacion(reparacion: Reparacion) {
        reparacionRepository.update(reparacion)
    }

    // Eliminar una reparación por ID
    fun deleteReparacion(id: Int) {
        reparacionRepository.delete(id)
    }
}

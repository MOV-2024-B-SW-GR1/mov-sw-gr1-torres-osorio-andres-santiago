package ec.epn.edu.Service

import ec.epn.edu.Model.Reparacion
import ec.epn.edu.Model.Vehiculo
import ec.epn.edu.Repository.ReparacionRepository
import java.util.Date

class ReparacionService(private val reparacionRepository: ReparacionRepository = ReparacionRepository()) {


    fun createReparacion(descripcion: String, costo: Double, mecanico: String, vehiculo: Vehiculo, fecha: Date): Reparacion {

        val reparacion = Reparacion(
            id = reparacionRepository.generateId(),
            descripcion = descripcion,
            costo = costo,
            fecha = fecha,
            mecanico = mecanico,
            vehiculo = vehiculo
        )
        reparacionRepository.save(reparacion)
        return reparacion
    }


    fun getAllReparaciones(): List<Reparacion> = reparacionRepository.getAll()


    fun getReparacionById(id: Int): Reparacion? = reparacionRepository.getById(id)


    fun updateReparacion(reparacion: Reparacion) {
        reparacionRepository.update(reparacion)
    }


    fun deleteReparacion(id: Int) {
        reparacionRepository.delete(id)
    }
}

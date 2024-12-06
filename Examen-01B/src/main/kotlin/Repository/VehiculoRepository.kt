package ec.epn.edu.Repository

import ec.epn.edu.Model.Vehiculo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class VehiculoRepository {

    private val filePath = "src/main/resources/json/vehiculos.json"

    private val gson = Gson()
    private val vehicleListType = object : TypeToken<List<Vehiculo>>() {}.type

    fun save(vehiculo: Vehiculo) {
        val vehiculos = getAll().toMutableList()
        vehiculos.add(vehiculo)
        writeToFile(vehiculos)
    }

    fun getAll(): List<Vehiculo> {
        val file = File(filePath)
        if (file.exists()) {
            return gson.fromJson(file.readText(), vehicleListType)
        }
        return emptyList()
    }

    fun getById(id: Int): Vehiculo? {
        return getAll().firstOrNull { it.id == id }
    }

    fun delete(id: Int) {
        val vehiculos = getAll().filterNot { it.id == id }
        writeToFile(vehiculos)
    }
    fun update(vehiculo: Vehiculo) {
        val vehiculos = getAll().map { existingVehiculo ->
            if (existingVehiculo.id == vehiculo.id) vehiculo else existingVehiculo
        }
        writeToFile(vehiculos)
    }

    private fun writeToFile(vehiculos: List<Vehiculo>) {
        val json = gson.toJson(vehiculos)
        File(filePath).writeText(json)
    }

    fun generateId(): Int {
        return (getAll().maxOfOrNull { it.id } ?: 0) + 1
    }
}

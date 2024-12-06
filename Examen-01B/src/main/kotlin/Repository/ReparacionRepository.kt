package ec.epn.edu.Repository

import ec.epn.edu.Model.Reparacion
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.Date

class ReparacionRepository {

    private val filePath = "src/main/resources/json/reparaciones.json"

    // Configuramos Gson para manejar fechas correctamente
    private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
    private val reparacionListType = object : TypeToken<List<Reparacion>>() {}.type

    fun save(reparacion: Reparacion) {
        val reparaciones = getAll().toMutableList()
        reparaciones.add(reparacion)
        writeToFile(reparaciones)
    }

    fun getAll(): List<Reparacion> {
        val file = File(filePath)
        if (file.exists()) {
            return gson.fromJson(file.readText(), reparacionListType)
        }
        return emptyList()
    }

    fun getById(id: Int): Reparacion? {
        return getAll().firstOrNull { it.id == id }
    }

    fun update(reparacion: Reparacion) {
        val reparaciones = getAll().map {
            if (it.id == reparacion.id) reparacion else it
        }
        writeToFile(reparaciones)
    }

    fun delete(id: Int) {
        val reparaciones = getAll().filterNot { it.id == id }
        writeToFile(reparaciones)
    }

    private fun writeToFile(reparaciones: List<Reparacion>) {
        val json = gson.toJson(reparaciones)
        File(filePath).writeText(json)
    }

    fun generateId(): Int {
        return (getAll().maxOfOrNull { it.id } ?: 0) + 1
    }
}

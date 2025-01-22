package com.example.vehiculos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.sql.Date
import java.text.SimpleDateFormat

class VehiculoReparacionDatabase(
    context: Context?
): SQLiteOpenHelper(
    context,
    "vehiculo_reparacion_db",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("PRAGMA foreign_keys = ON;")
        val scriptSQLCrearTablaVehiculo =
            """
                CREATE TABLE VEHICULO(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    marca VARCHAR(50),
                    modelo VARCHAR(50),
                    año INTEGER,
                    color VARCHAR(20)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaVehiculo)

        val scriptSQLCrearTablaReparacion =
            """
                CREATE TABLE REPARACION(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    descripcion VARCHAR(100),
                    costo DECIMAL(10, 2),
                    fecha DATE,
                    mecanico VARCHAR(50),
                    vehiculoId INTEGER,
                    FOREIGN KEY(vehiculoId) REFERENCES VEHICULO(id) ON DELETE CASCADE
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaReparacion)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    // Vehiculo
    fun obtenerTodosLosVehiculos(): List<Vehiculo> {
        val listaVehiculos = mutableListOf<Vehiculo>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM VEHICULO"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val marca = resultadoConsultaLectura.getString(1)
                val modelo = resultadoConsultaLectura.getString(2)
                val año = resultadoConsultaLectura.getInt(3)
                val color = resultadoConsultaLectura.getString(4)

                val vehiculo = Vehiculo(id, marca, modelo, año, color)
                listaVehiculos.add(vehiculo)
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaVehiculos
    }

    fun crearVehiculo(marca: String, modelo: String, año: Int, color: String): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresGuardar = ContentValues()
        valoresGuardar.put("marca", marca)
        valoresGuardar.put("modelo", modelo)
        valoresGuardar.put("año", año)
        valoresGuardar.put("color", color)
        val resultadoGuardar = baseDatosEscritura.insert("VEHICULO", null, valoresGuardar)
        baseDatosEscritura.close()
        return resultadoGuardar != -1L
    }

    fun eliminarVehiculo(id: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminar = baseDatosEscritura.delete("VEHICULO", "id=?", parametrosConsultaDelete)
        baseDatosEscritura.close()
        return resultadoEliminar != -1
    }

    fun actualizarVehiculo(id: Int, marca: String, modelo: String, año: Int, color: String): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("marca", marca)
        valoresAActualizar.put("modelo", modelo)
        valoresAActualizar.put("año", año)
        valoresAActualizar.put("color", color)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizar = baseDatosEscritura.update("VEHICULO", valoresAActualizar, "id=?", parametrosConsultaActualizar)
        baseDatosEscritura.close()
        return resultadoActualizar != -1
    }

    // Reparacion
    fun obtenerReparacionesPorVehiculo(vehiculoId: Int): List<Reparacion> {
        val listaReparaciones = mutableListOf<Reparacion>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM REPARACION WHERE vehiculoId = ?"
        val parametrosConsultaLectura = arrayOf(vehiculoId.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)

        val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // Formato de fecha ISO

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val descripcion = resultadoConsultaLectura.getString(1)
                val costo = resultadoConsultaLectura.getDouble(2)

                // Convertir la fecha en cadena a Date usando SimpleDateFormat
                val fechaString = resultadoConsultaLectura.getString(3)
                val fecha = formatoFecha.parse(fechaString)

                val mecanico = resultadoConsultaLectura.getString(4)

                val reparacion = Reparacion(id, descripcion, costo, fecha, mecanico, vehiculoId)
                listaReparaciones.add(reparacion)
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaReparaciones
    }

    fun crearReparacion(descripcion: String, costo: Double, fecha: Date, mecanico: String, vehiculoId: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresGuardar = ContentValues()
        valoresGuardar.put("descripcion", descripcion)
        valoresGuardar.put("costo", costo)

        // Convertir Date a String en formato ISO
        val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        valoresGuardar.put("fecha", formatoFecha.format(fecha))

        valoresGuardar.put("mecanico", mecanico)
        valoresGuardar.put("vehiculoId", vehiculoId)
        val resultadoGuardar = baseDatosEscritura.insert("REPARACION", null, valoresGuardar)
        baseDatosEscritura.close()
        return resultadoGuardar != -1L
    }

    fun eliminarReparacion(id: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminar = baseDatosEscritura.delete("REPARACION", "id=?", parametrosConsultaDelete)
        baseDatosEscritura.close()
        return resultadoEliminar != -1
    }

    fun actualizarReparacion(
        id: Int,
        descripcion: String,
        costo: Double,
        fecha: Date,
        mecanico: String,
        vehiculoId: Int
    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("descripcion", descripcion)
        valoresAActualizar.put("costo", costo)

        // Convertir la fecha a formato 'yyyy-MM-dd'
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fechaString = sdf.format(fecha)
        valoresAActualizar.put("fecha", fechaString)

        valoresAActualizar.put("mecanico", mecanico)
        valoresAActualizar.put("vehiculoId", vehiculoId)

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizar = baseDatosEscritura.update("REPARACION", valoresAActualizar, "id=?", parametrosConsultaActualizar)
        baseDatosEscritura.close()

        return resultadoActualizar != -1
    }
}

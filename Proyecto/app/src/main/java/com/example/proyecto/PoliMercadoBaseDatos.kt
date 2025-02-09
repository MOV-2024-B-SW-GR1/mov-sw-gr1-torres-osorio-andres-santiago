package com.example.proyecto

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PoliMercadoBaseDatos (
    context: Context?
) : SQLiteOpenHelper(
    context,
    "polimercado_db",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaUsuario =
            """
                CREATE TABLE USUARIO(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    correo VARCHAR(100),
                    contraseña VARCHAR(100),
                    telefono VARCHAR(15),
                    direccion VARCHAR(150)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaUsuario)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun obtenerTodosLosUsuarios(): List<Usuario> {
        val listaUsuarios: MutableList<Usuario> = ArrayList()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM USUARIO"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val correo = resultadoConsultaLectura.getString(2)
                val contraseña = resultadoConsultaLectura.getString(3)
                val telefono = resultadoConsultaLectura.getString(4)
                val direccion = resultadoConsultaLectura.getString(5)
                val usuario = Usuario(id, nombre, correo, contraseña, telefono, direccion)
                listaUsuarios.add(usuario)
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaUsuarios
    }

    fun crearUsuario(
        nombre: String?,
        correo: String?,
        contraseña: String?,
        telefono: String?,
        direccion: String?
    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresGuardar = ContentValues()
        valoresGuardar.put("nombre", nombre)
        valoresGuardar.put("correo", correo)
        valoresGuardar.put("contraseña", contraseña)
        valoresGuardar.put("telefono", telefono)
        valoresGuardar.put("direccion", direccion)
        val resultadoGuardar = baseDatosEscritura.insert("USUARIO", null, valoresGuardar)
        baseDatosEscritura.close()
        return resultadoGuardar != -1L
    }

    fun validarUsuario(nombre: String, contraseña: String): Boolean {
        val baseDatosLectura = readableDatabase
        val scriptConsulta = "SELECT COUNT(*) FROM USUARIO WHERE nombre = ? AND contraseña = ?"
        val parametrosConsulta = arrayOf(nombre, contraseña)

        val cursor = baseDatosLectura.rawQuery(scriptConsulta, parametrosConsulta)
        var existeUsuario = false

        if (cursor.moveToFirst()) {
            val count = cursor.getInt(0)
            existeUsuario = count > 0
        }

        cursor.close()
        baseDatosLectura.close()
        return existeUsuario
    }
}
package com.example.vehiculos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vehiculos.R
import com.example.vehiculos.VehiculoReparacionDatabase
import com.example.vehiculos.bdVehiculoReparacion
import com.google.android.material.snackbar.Snackbar
import java.util.Date

class ReparacionOperaciones : AppCompatActivity() {

    private fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_operaciones_reparaciones),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.setAction("Cerrar") { snack.dismiss() }
        snack.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_operaciones_reparaciones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_operaciones_reparaciones)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val modo = intent.getStringExtra("modo") ?: "crear"
        val reparacion: Reparacion? = intent.getParcelableExtra("reparacion")

        val botonGuardarReparacion = findViewById<Button>(R.id.btn_guardar_reparaciones)
        val inputDescripcion = findViewById<EditText>(R.id.text_descripcion_reparaciones)
        val inputCosto = findViewById<EditText>(R.id.text_costo_reparaciones)
        val inputFecha = findViewById<EditText>(R.id.text_fecha_reparaciones)
        val inputMecanico = findViewById<EditText>(R.id.text_mecanico_reparaciones)
        val inputVehiculoId = findViewById<EditText>(R.id.text_numero_reparaciones)

        if (modo == "editar" && reparacion != null) {
            inputDescripcion.setText(reparacion.descripcion)
            inputCosto.setText(reparacion.costo.toString())
            inputFecha.setText(reparacion.fecha.toString())
            inputMecanico.setText(reparacion.mecanico)
            inputVehiculoId.setText(reparacion.vehiculoId.toString())
            botonGuardarReparacion.text = "Actualizar"
        } else {
            botonGuardarReparacion.text = "Crear"
        }

        botonGuardarReparacion.setOnClickListener {
            val descripcion = inputDescripcion.text.toString().trim()
            val costoString = inputCosto.text.toString().trim()
            val fechaString = inputFecha.text.toString().trim()
            val mecanico = inputMecanico.text.toString().trim()
            val vehiculoIdString = inputVehiculoId.text.toString().trim()

            if (descripcion.isEmpty() || costoString.isEmpty() || fechaString.isEmpty() || mecanico.isEmpty() || vehiculoIdString.isEmpty()) {
                mostrarSnackbar("Por favor, llene todos los campos.")
                return@setOnClickListener
            }

            val costo = costoString.toDoubleOrNull()
            if (costo == null || costo < 0) {
                mostrarSnackbar("Por favor, ingrese un costo válido.")
                return@setOnClickListener
            }

            val vehiculoId = vehiculoIdString.toIntOrNull()
            if (vehiculoId == null || vehiculoId < 0) {
                mostrarSnackbar("Por favor, ingrese un ID de vehículo válido.")
                return@setOnClickListener
            }

            if (modo == "crear") {
                val fecha = Date()
                val fechaSql = java.sql.Date(fecha.time)
                val respuesta = bdVehiculoReparacion.tablaVehiculoReparacion?.crearReparacion(
                    descripcion,
                    costo,
                    fechaSql,
                    mecanico,
                    vehiculoId
                )

                if (respuesta == true) {
                    mostrarSnackbar("Reparación creada exitosamente.")
                    setResult(RESULT_OK)
                    finish()
                } else {
                    mostrarSnackbar("Error al crear la reparación.")
                }
            } else if (modo == "editar" && reparacion != null) {
                val fecha = Date()
                val fechaSql = java.sql.Date(fecha.time)
                val respuesta = bdVehiculoReparacion.tablaVehiculoReparacion?.actualizarReparacion(
                    reparacion.id,
                    descripcion,
                    costo,
                    fechaSql,
                    mecanico,
                    vehiculoId
                )

                if (respuesta == true) {
                    mostrarSnackbar("Reparación actualizada exitosamente.")
                    setResult(RESULT_OK)
                    finish()
                } else {
                    mostrarSnackbar("Error al actualizar la reparación.")
                }
            }
        }
    }
}

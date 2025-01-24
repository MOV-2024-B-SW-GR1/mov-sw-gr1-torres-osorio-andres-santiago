package com.example.vehiculos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vehiculos.R
import com.example.vehiculos.Vehiculo
import com.example.vehiculos.VehiculoReparacionDatabase
import com.example.vehiculos.bdVehiculoReparacion
import com.google.android.material.snackbar.Snackbar

class VehiculosOperaciones : AppCompatActivity() {

    private fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_operaciones_vehiculos),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.setAction("Cerrar") { snack.dismiss() }
        snack.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_operaciones_vehiculos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_operaciones_vehiculos)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val modo = intent.getStringExtra("modo") ?: "crear"
        val vehiculo: Vehiculo? = intent.getParcelableExtra("vehiculo")

        val botonGuardarVehiculo = findViewById<Button>(R.id.btn_guardar_vehiculos)
        val inputMarca = findViewById<EditText>(R.id.text_marca_vehiculo)
        val inputModelo = findViewById<EditText>(R.id.text_modelo_vehiculo)
        val inputAno = findViewById<EditText>(R.id.text_año_vehiculo)
        val inputColor = findViewById<EditText>(R.id.text_color_vehiculo)

        if (modo == "editar" && vehiculo != null) {
            inputMarca.setText(vehiculo.marca)
            inputModelo.setText(vehiculo.modelo)
            inputAno.setText(vehiculo.año.toString())
            inputColor.setText(vehiculo.color)
            botonGuardarVehiculo.text = "Actualizar"
        } else {
            botonGuardarVehiculo.text = "Crear"
        }

        botonGuardarVehiculo.setOnClickListener {
            val marca = inputMarca.text.toString().trim()
            val modelo = inputModelo.text.toString().trim()
            val anoString = inputAno.text.toString().trim()
            val color = inputColor.text.toString().trim()

            if (marca.isEmpty() || modelo.isEmpty() || anoString.isEmpty() || color.isEmpty()) {
                mostrarSnackbar("Por favor, llene todos los campos.")
                return@setOnClickListener
            }

            val ano = anoString.toIntOrNull()
            if (ano == null || ano < 1886) {
                mostrarSnackbar("Por favor, ingrese un año válido (mayor a 1885).")
                return@setOnClickListener
            }

            if (modo == "crear") {
                val respuesta = bdVehiculoReparacion.tablaVehiculoReparacion?.crearVehiculo(
                    marca,
                    modelo,
                    ano,
                    color
                )

                if (respuesta == true) {
                    mostrarSnackbar("Vehículo creado exitosamente.")
                    setResult(RESULT_OK)
                    finish()
                } else {
                    mostrarSnackbar("Error al crear el vehículo.")
                }
            } else if (modo == "editar" && vehiculo != null) {
                val respuesta = bdVehiculoReparacion.tablaVehiculoReparacion?.actualizarVehiculo(
                    vehiculo.id,
                    marca,
                    modelo,
                    ano,
                    color
                )

                if (respuesta == true) {
                    mostrarSnackbar("Vehículo actualizado exitosamente.")
                    setResult(RESULT_OK)
                    finish()
                } else {
                    mostrarSnackbar("Error al actualizar el vehículo.")
                }
            }
        }
    }
}

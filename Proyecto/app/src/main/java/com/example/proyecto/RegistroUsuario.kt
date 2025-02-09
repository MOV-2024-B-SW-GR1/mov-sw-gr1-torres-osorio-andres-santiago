package com.example.proyecto

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class RegistroUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_registro)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonRegistrarUsuario = findViewById<Button>(R.id.btn_RegistrarUsuario)
        val inputNombre = findViewById<EditText>(R.id.txt_nombreUsuario)
        val inputCorreo = findViewById<EditText>(R.id.txt_correoUsuario)
        val inputContraseña = findViewById<EditText>(R.id.txt_contraseñaUsuario)
        val inputTelefono = findViewById<EditText>(R.id.txt_telefonoUsuario)
        val inputDireccion = findViewById<EditText>(R.id.txt_direccionUsuario)

        botonRegistrarUsuario.setOnClickListener{
            val nombre = inputNombre.text.toString().trim()
            val correo = inputCorreo.text.toString().trim()
            val contraseña = inputContraseña.text.toString().trim()
            val telefono = inputTelefono.text.toString().trim()
            val direccion = inputDireccion.text.toString().trim()

            if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || contraseña.isEmpty() || direccion.isEmpty()) {
                mostrarSnackbar("Por favor, llene todos los campos.")
                return@setOnClickListener
            }

            val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@epn\\.edu\\.ec$")
            if (!correo.matches(emailRegex)) {
                mostrarSnackbar("El correo debe tener el formato 'nombre@epn.edu.ec'.")
                return@setOnClickListener
            }

            val telefonoRegex = Regex("^[0-9]{10}$")
            if (!telefono.matches(telefonoRegex)) {
                mostrarSnackbar("El teléfono solo debe contener números y tener 10 dígitos.")
                return@setOnClickListener
            }

            val respuesta = BaseDeDatos.tablaPoliMercado?.crearUsuario(
                nombre,
                correo,
                contraseña,
                telefono,
                direccion
            )

            if (respuesta == true) {
                mostrarSnackbar("Usuario creado exitosamente.")
                botonRegistrarUsuario.postDelayed({
                    setResult(RESULT_OK)
                    finish()
                }, 3000)
            } else {
                mostrarSnackbar("Error al crear el usuario.")
            }
        }

    }

    private fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_registro),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.setAction("Cerrar") { snack.dismiss() }
        snack.show()
    }
}
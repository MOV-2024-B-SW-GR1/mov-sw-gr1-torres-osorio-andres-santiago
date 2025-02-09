package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        BaseDeDatos.tablaPoliMercado = PoliMercadoBaseDatos(this)

        val botonRegistrar = findViewById<Button>(R.id.btn_registrar_usuario)
        botonRegistrar
            .setOnClickListener {
                irActividad(RegistroUsuario::class.java)
            }

        val botonIngresar = findViewById<Button>(R.id.btn_login)
        val inputNombreI = findViewById<EditText>(R.id.txt_username)
        val inputPassword = findViewById<EditText>(R.id.txt_password)

        botonIngresar.setOnClickListener{
            val nombre = inputNombreI.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            if (nombre.isEmpty() || password.isEmpty()) {
                mostrarSnackbar("Por favor, llene todos los campos.")
                return@setOnClickListener
            }

            if (BaseDeDatos.tablaPoliMercado?.validarUsuario(nombre, password) == true) {
                mostrarSnackbar("Inicio de sesión exitoso")
                irActividad(Dashboard::class.java)
            } else {
                mostrarSnackbar("Nombre de usuario o contraseña incorrectos")
            }
        }
    }

    fun irActividad(clase:Class<*>){
        startActivity(Intent(this, clase))
    }

    private fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_login),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.setAction("Cerrar") { snack.dismiss() }
        snack.show()
    }
}
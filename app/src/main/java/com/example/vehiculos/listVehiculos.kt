package com.example.vehiculos

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class listVehiculos : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val listaVehiculos = mutableListOf<Vehiculo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_vehiculos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_lista_vehiculos)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView = findViewById(R.id.lv_vehiculos)
        val botonAnadirVehiculo = findViewById<Button>(R.id.btn_crear_vehiculo)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaVehiculos.map { it.marca })
        listView.adapter = adapter

        registerForContextMenu(listView)

        cargarDatosDesdeBaseDeDatos()

        botonAnadirVehiculo.setOnClickListener {
            irActividad(VehiculosOperaciones::class.java) // Pasa el requestCode 1
        }
    }

    var posicionItemSeleccionado = -1 // VARIABLE GLOBAL
    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // llenamos opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_menu, menu)
        // obtener id
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            cargarDatosDesdeBaseDeDatos() // Refresca la lista
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editar -> {
                val vehiculoSeleccionado = listaVehiculos[posicionItemSeleccionado]
                irActividad(VehiculosOperaciones::class.java, vehiculoSeleccionado)
                return true
            }
            R.id.eliminar -> {
                abrirDialogo()
                return true
            }
            R.id.verReparaciones -> {
                val vehiculoSeleccionado = listaVehiculos[posicionItemSeleccionado]
                irActividad(listReparaciones::class.java, vehiculoSeleccionado)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_lista_vehiculos),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.setAction("Cerrar") { snack.dismiss() }
        snack.show()
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea Eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->
                val vehiculoSeleccionado = listaVehiculos[posicionItemSeleccionado]
                val id = vehiculoSeleccionado.id

                // Llamar al método de eliminación
                val eliminado = bdVehiculoReparacion.tablaVehiculoReparacion?.eliminarVehiculo(id)
                if (eliminado == true) {
                    mostrarSnackbar("Vehículo eliminado correctamente.")
                    cargarDatosDesdeBaseDeDatos() // Refrescar la lista
                } else {
                    mostrarSnackbar("Error al eliminar el vehículo.")
                }
            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val dialogo = builder.create()
        dialogo.show()
    }

    fun cargarDatosDesdeBaseDeDatos() {
        val vehiculos = bdVehiculoReparacion.tablaVehiculoReparacion?.obtenerTodosLosVehiculos()
        listaVehiculos.clear()
        if (vehiculos != null) {
            listaVehiculos.addAll(vehiculos)
        }
        adapter.clear()
        adapter.addAll(listaVehiculos.map { it.marca })
        adapter.notifyDataSetChanged()
    }

    fun irActividad(clase: Class<*>, vehiculo: Vehiculo? = null) {
        val intentExplicito = Intent(this, clase)
        if (vehiculo != null) {
            intentExplicito.putExtra("modo", "editar")
            intentExplicito.putExtra("vehiculo", vehiculo)
        } else {
            intentExplicito.putExtra("modo", "crear")
        }
        startActivityForResult(intentExplicito, 1)
    }
}

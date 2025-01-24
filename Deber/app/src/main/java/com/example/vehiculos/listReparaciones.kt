package com.example.vehiculos

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Locale

class listReparaciones : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val listaReparaciones = mutableListOf<Reparacion>()
    private var idVehiculo = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_reparaciones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_list_reparaciones)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView = findViewById(R.id.lv_reparaciones)
        val txtReparaciones = findViewById<TextView>(R.id.txt_reparaciones)
        val btnAnadirReparacion = findViewById<Button>(R.id.btn_crear_reparaciones)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaReparaciones.map { it.descripcion })
        listView.adapter = adapter

        val vehiculo = intent.getParcelableExtra<Vehiculo>("vehiculo")
        if (vehiculo != null) {
            idVehiculo = vehiculo.id
        }
        txtReparaciones.setText("${vehiculo?.marca?.toUpperCase(Locale.ROOT)}'S REPARACIONES")
        registerForContextMenu(listView)
        cargarDatosDesdeBaseDeDatos(idVehiculo)

        btnAnadirReparacion.setOnClickListener {
            irActividad(ReparacionOperaciones::class.java)
        }
    }

    var posicionItemSeleccionado = -1
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // llenamos opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_menu, menu)
        menu?.findItem(R.id.verReparaciones)?.isVisible = false
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            cargarDatosDesdeBaseDeDatos(idVehiculo) // Refresca la lista
        }
    }

    override fun onContextItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editar -> {
                val reparacionSeleccionada = listaReparaciones[posicionItemSeleccionado]
                irActividad(ReparacionOperaciones::class.java, reparacionSeleccionada)
                return true
            }

            R.id.eliminar -> {
                abrirDialogo()
                return true
            }

            R.id.verReparaciones -> {
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_list_reparaciones),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.setAction("Cerrar") { snack.dismiss() }
        snack.show()
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ dialog, which ->
                val reparacionSeleccionada = listaReparaciones[posicionItemSeleccionado]
                val id = reparacionSeleccionada.id

                // Llamar al método de eliminación
                val eliminado = bdVehiculoReparacion.tablaVehiculoReparacion?.eliminarReparacion(id)
                if (eliminado == true) {
                    mostrarSnackbar("Reparación eliminada correctamente.")
                    cargarDatosDesdeBaseDeDatos(idVehiculo) // Refrescar la lista
                } else {
                    mostrarSnackbar("Error al eliminar la reparación.")
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

    fun cargarDatosDesdeBaseDeDatos(idVehiculo: Int) {
        val reparaciones = bdVehiculoReparacion.tablaVehiculoReparacion?.obtenerReparacionesPorVehiculo(idVehiculo)
        listaReparaciones.clear()
        if (reparaciones != null) {
            listaReparaciones.addAll(reparaciones)
        }
        adapter.clear()
        adapter.addAll(listaReparaciones.map { reparacion ->
            // Use SimpleDateFormat to format the date
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            "${reparacion.descripcion} - ${sdf.format(reparacion.fecha)}"
        })
        adapter.notifyDataSetChanged()
    }

    fun irActividad(clase: Class<*>, reparacion: Reparacion? = null) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("idVehiculo", idVehiculo)

        if (reparacion != null) {
            intentExplicito.putExtra("modo", "editar")
            intentExplicito.putExtra("reparacion", reparacion)
        } else {
            intentExplicito.putExtra("modo", "crear")
        }

        startActivityForResult(intentExplicito, 1)
    }
}

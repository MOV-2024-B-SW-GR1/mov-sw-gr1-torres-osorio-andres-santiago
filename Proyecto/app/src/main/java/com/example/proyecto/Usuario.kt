package com.example.proyecto

import android.os.Parcel
import android.os.Parcelable

class Usuario(
    val id: Int,
    val nombre: String,
    var correo: String,
    var contraseña: String,
    var telefono: String,
    val direccion: String
) : Parcelable {

    // Constructor que recibe un Parcel
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    // Escribir los datos en el Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(correo)
        parcel.writeString(contraseña)
        parcel.writeString(telefono)
        parcel.writeString(direccion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }
}
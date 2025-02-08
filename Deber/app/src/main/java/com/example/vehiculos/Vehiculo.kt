package com.example.vehiculos

import android.os.Parcel
import android.os.Parcelable

class Vehiculo (
    val id: Int,
    val marca: String,
    val modelo: String,
    val año: Int,
    val color: String,
    val latitud: Double,
    val longitud: Double): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }
    override fun toString(): String {
        return "Modelo: $modelo Marca: $marca  Año: $año "
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(marca)
        parcel.writeString(modelo)
        parcel.writeInt(año)
        parcel.writeString(color)
        parcel.writeDouble(latitud)
        parcel.writeDouble(longitud)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<Vehiculo> {
        override fun createFromParcel(parcel: Parcel): Vehiculo {
            return Vehiculo(parcel)
        }

        override fun newArray(size: Int): Array<Vehiculo?> {
            return arrayOfNulls(size)
        }
    }
}
package com.example.vehiculos

import android.os.Parcel
import android.os.Parcelable

class Vehiculo (
    val id: Int,
    val marca: String,
    val modelo: String,
    val año: Int,
    val color: String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
    ) {
    }
    override fun toString(): String {
        return modelo
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(marca)
        parcel.writeString(modelo)
        parcel.writeInt(año)
        parcel.writeString(color)
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
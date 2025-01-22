package com.example.vehiculos

import android.os.Parcel
import android.os.Parcelable
import java.util.Date


class Reparacion(
    val id: Int,
    val descripcion: String,
    val costo: Double,
    val fecha: Date,
    val mecanico: String,
    val vehiculoId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble(),
        Date(parcel.readLong()),
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(descripcion)
        parcel.writeDouble(costo)
        parcel.writeLong(fecha.time)
        parcel.writeString(mecanico)
        parcel.writeInt(vehiculoId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reparacion> {
        override fun createFromParcel(parcel: Parcel): Reparacion {
            return Reparacion(parcel)
        }

        override fun newArray(size: Int): Array<Reparacion?> {
            return arrayOfNulls(size)
        }
    }

}
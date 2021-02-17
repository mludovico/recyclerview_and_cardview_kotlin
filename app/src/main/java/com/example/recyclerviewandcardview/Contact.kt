package com.example.recyclerviewandcardview

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact (
    val name: String,
    val phone: String,
    val picture: String
): Parcelable
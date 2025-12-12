package com.example.thirdeye.data.models

import android.graphics.Bitmap
import android.os.Parcelable
import java.io.File

@kotlinx.android.parcel.Parcelize
data class IntrudersImages(
    val id: String,
    val bitmap: Bitmap,
    val isLocked: Boolean,
    val file: File
): Parcelable

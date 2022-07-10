package com.example.shoestoreinventory.models

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.ObservableField
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Shoe(
    var name: String,
    var size: String,
    var company: String,
    var description: String,
    var image: Bitmap,
    val size_list: List<String> = listOf("9","10","11","12")
) : Parcelable
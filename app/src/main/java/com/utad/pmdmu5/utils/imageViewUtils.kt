package com.utad.pmdmu5.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadFromUrl(url: String, context: Context) {
    Glide.with(context)// Raiz de la vista donde estamos en holder en este caso
        .load(url) // Enlace a la  vista
        .into(this) // Donde va pintar la imagen
}
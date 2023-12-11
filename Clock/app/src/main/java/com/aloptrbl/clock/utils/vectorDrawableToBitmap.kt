package com.aloptrbl.clock.utils

import android.graphics.Bitmap
import android.graphics.drawable.VectorDrawable
import androidx.compose.ui.unit.IntSize

fun vectorDrawableToBitmap(vectorDrawable: VectorDrawable, size: IntSize): Bitmap {
    val bitmap = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)
    return bitmap
}


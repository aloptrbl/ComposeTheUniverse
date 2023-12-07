package com.aloptrbl.clock.utils

import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin

fun PolarToCartesian(radius: Float, angleInDegrees: Float): Offset {
    val angleInRadians = Math.toRadians(angleInDegrees.toDouble())
    val x = radius * cos(angleInRadians)
    val y = radius * sin(angleInRadians)
    return Offset(x.toFloat(), y.toFloat())
}

fun getOffsetCoordinates(radius: Double, angleInDegrees: Double): Pair<Double, Double> {
    val adjustedAngleInDegrees = angleInDegrees - 90.0
    val angleInRadians = Math.toRadians(adjustedAngleInDegrees)
    val x = radius * cos(angleInRadians)
    val y = radius * sin(angleInRadians)
    return Pair(x, y)
}
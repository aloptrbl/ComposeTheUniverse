package com.aloptrbl.clock.ui.components.analogs

import android.os.Build
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.aloptrbl.clock.utils.PolarToCartesian
import com.aloptrbl.clock.utils.getOffsetCoordinates
import com.aloptrbl.clock.utils.toRadians
import com.aloptrbl.clock.utils.toRoman
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.cos
import kotlin.math.sin
@Composable
fun AnalogClockWithRomanNumeralsView() {
    var coordinate by remember { mutableStateOf<Pair<Double, Double>>(Pair(0.0, 0.0)) }
    var coordinateMove by remember { mutableStateOf(Pair(0.0, 0.0)) }
    val stroke = Stroke(width = 5f)
    val minuteHandColor = Color.Black
    val minuteHandLengthRatio = 0.9f
    var time by remember { mutableStateOf(LocalTime.now()) }
    var oldTime by remember { mutableStateOf("") }
    val hourAngle = remember { mutableStateOf(0f) }
    val minuteAngle = remember { mutableStateOf(0f) }
    val secondAngle = remember { mutableStateOf(0f) }
    val transition = rememberInfiniteTransition()
    val alpha by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val transitionTime = updateTransition(targetState = time, label = "TimeTransition")

    hourAngle.value = (time.hour % 12 + time.minute / 60f) * 360f / 12f - 90f
    minuteAngle.value = (time.minute + time.second / 60f) * 360f / 60f - 90f
    secondAngle.value = time.second * 360f / 60f - 90f

    LaunchedEffect(Unit) {
        while (true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                time = LocalTime.now()
            }
            delay(1000)
        }
    }

    Column(
        Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Clock version 1.0
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.clip(RoundedCornerShape(150.dp)).size(300.dp).background(
                Color.White
            ).pointerInput(Unit) {
                awaitEachGesture {
                    val event = awaitPointerEvent()
                    val current = event.changes.firstOrNull()
                    coordinateMove = Pair(
                        current?.position?.x?.toDouble(),
                        current?.position?.y?.toDouble()
                    ) as Pair<Double, Double>
                }
            }) {
            for (i in 0 until 24) {
                val hour = if (i % 12 == 0) 12 else i % 12
                val degree = hour * 30.0

                val coordinate90 = getOffsetCoordinates(120.0, degree)
                coordinate = coordinate90
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(30.dp)
                        .offset(coordinate90.first.dp, coordinate90.second.dp)
                        .clip(RoundedCornerShape(100))
                        .background(Color.Unspecified)

                ) {
                    Text("${hour.toRoman()}")
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val hour = time.format(DateTimeFormatter.ofPattern("h"))
                Box(modifier = Modifier.size(300.dp), contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawLine(
                            Color.Black,
                            center,
                            Offset(
                                center.x + 150f * cos(hourAngle.value.toRadians()),
                                center.y + 150f * sin(hourAngle.value.toRadians())
                            ),
                            strokeWidth = 8f
                        )
                        drawLine(
                            Color.Black,
                            center,
                            Offset(
                                center.x + 170f * cos(minuteAngle.value.toRadians()),
                                center.y + 170f * sin(minuteAngle.value.toRadians())
                            ),
                            strokeWidth = 4f
                        )
                        drawLine(
                            Color.Red,
                            center,
                            Offset(
                                center.x + 190f * cos(secondAngle.value.toRadians()),
                                center.y + 190f * sin(secondAngle.value.toRadians())
                            ),
                            strokeWidth = 2f
                        )
                        val radius = size.minDimension / 2
                        val center = Offset(size.width / 2, size.height / 2)

                        // Draw minute lines
                        for (i in 0 until 60) {
                            val angle = i * 6f
                            val innerRadius = if (i % 5 == 0) radius * 0.85f else radius * 0.95f
                            val outerRadius = radius

                            val innerPoint = center + PolarToCartesian(innerRadius, angle)
                            val outerPoint = center + PolarToCartesian(outerRadius, angle)

                            drawLine(
                                color = minuteHandColor,
                                start = innerPoint,
                                end = outerPoint,
                                strokeWidth = stroke.width,
                                cap = stroke.cap
                            )
                        }
                    }
                }
            }
        }
    }
}
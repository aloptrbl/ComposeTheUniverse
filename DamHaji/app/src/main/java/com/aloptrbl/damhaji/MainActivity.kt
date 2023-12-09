package com.aloptrbl.damhaji

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.spring
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.aloptrbl.damhaji.ui.theme.DamHajiTheme
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DamHajiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Test()
                }
            }
        }
    }
}

@Composable
fun Test() {
    val chessPiece = remember { mutableStateOf(DraggablePiece(Pair(0, 0), "♜")) }
    val lightColor = Color(0xFFDDB88C)
    val darkColor = Color(0xFFAA6A39)
    var text by remember { mutableStateOf("zzzzzz") }
    var insideText by remember { mutableStateOf("-") }
    var offsets = remember { mutableStateListOf<Rect>() }
    val coroutineScope = rememberCoroutineScope()
    var currentIndex = remember { mutableStateOf(0) }

    Column() {
        Box(Modifier.fillMaxWidth()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(10),
                modifier = Modifier
            ) {
                items(100) { index ->
                    val row = index / 10
                    val col = index % 10
                    val boxModifier = if (chessPiece.value.position == Pair(row, col)) {
                        Modifier
                            .size(50.dp)
                            .onGloballyPositioned { layoutCoordinates ->
                                val positionInParent = layoutCoordinates.boundsInParent()
                                val newOffset = Pair(positionInParent.center.x, positionInParent.center.y)

                                if(!offsets.contains(positionInParent)) {
                                    offsets.add(positionInParent)
                                }
                            }
                            .background(if ((row + col) % 2 == 0) lightColor else darkColor)
                    } else {
                        Modifier
                            .size(50.dp)
                            .onGloballyPositioned { layoutCoordinates ->
                                val positionInParent = layoutCoordinates.boundsInParent()
                                val newOffset = Pair(positionInParent.center.x, positionInParent.center.y)

                                if(!offsets.contains(positionInParent)) {
                                    offsets.add(positionInParent)
                                }
                            }
                            .background(if ((row + col) % 2 == 0) lightColor else darkColor)
                    }

                    Box(boxModifier, contentAlignment = Alignment.Center) {
                        Text(
                            text = "${'A' + col}${8 - row}",
                            color = if ((row + col) % 2 == 0) Color.Black else Color.White,
                            modifier = Modifier.alpha(0.0f)
                        )
                    }
                }
            }

            offsets.forEachIndexed { index, offset ->
                val row = index / 10
                val col = index % 10
                if (row < 4 || row >= 6) {
                    if ((row + col) % 2 == 0) {
                        var offsetX = remember { Animatable(offset.center.x.toInt() - 40f) }
                        var offsetY = remember { Animatable(offset.center.y.toInt() - 40f) }

                        var previousOffsetX = remember { mutableStateOf(offset.center.x.toInt() - 40f) }
                        var previousOffsetY = remember { mutableStateOf(offset.center.y.toInt() - 40f) }
                        val haptic = LocalHapticFeedback.current
                        val velocityTracker = VelocityTracker()
                        Box(
                            modifier = Modifier
                                .offset { IntOffset(offsetX.value.toInt(), offsetY.value.toInt()) }
                                .size(25.dp)
                                .clip(RoundedCornerShape(100))
                                .background(Color.Red.copy(0.4f))
                                .pointerInput(Unit) {
                                    detectDragGestures(
                                        onDrag = { change, dragAmount ->
                                            change.consume()
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                                            // The animation stops when it reaches the bounds.
                                            offsetX.updateBounds(
                                                lowerBound = offsetX.lowerBound,
                                                upperBound = offsetX.upperBound
                                            )

                                            offsetY.updateBounds(
                                                lowerBound = offsetY.lowerBound,
                                                upperBound = offsetY.upperBound
                                            )

                                            coroutineScope.launch {
                                                offsetX.snapTo(offsetX.value + dragAmount.x)
                                                offsetY.snapTo(offsetY.value + dragAmount.y)

                                                currentIndex.value = isInside(
                                                    Offset(
                                                        offsetX.value.toFloat(),
                                                        offsetY.value.toFloat()
                                                    ), offsets
                                                )
                                            }

                                            text =
                                                "The box is inside of x ${offsetX} & y ${offsetY} and Rect is ${offsets[1].topLeft.x} ${offsets[1].topLeft.y}"
                                        },
                                        onDragEnd = {
                                            // This block of code will be executed when the drag gesture ends
                                            // Check if the box is inside of it's offset
                                            insideText = "index is ${
                                                isInside(
                                                    Offset(
                                                        offsetX.value.toFloat(),
                                                        offsetY.value.toFloat()
                                                    ), offsets
                                                )
                                            }"

                                            val row = currentIndex.value / 10
                                            val col = currentIndex.value % 10

                                            if ((row + col) % 2 == 0) {
                                                coroutineScope.launch {
                                                    offsetX.animateTo(getCenterOffset(  Offset(
                                                        offsetX.value.toFloat(),
                                                        offsetY.value.toFloat()
                                                    ), offsets).x, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium))
                                                    offsetY.animateTo(  getCenterOffset(  Offset(
                                                        offsetX.value.toFloat(),
                                                        offsetY.value.toFloat()
                                                    ), offsets).y, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium))
                                                    previousOffsetX.value = offsetX.value
                                                    previousOffsetY.value = offsetY.value
                                                }
                                            } else {
                                                coroutineScope.launch {
                                                    offsetX.animateTo(previousOffsetX.value, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium))
                                                    offsetY.animateTo(previousOffsetY.value, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium))
                                                }
                                            }
                                        }
                                    )
                                }
                        )
                    }
                }
            }
        }
        Text("result")
        Text(text)

        Text("Lists")
        Text(insideText)

        Text("current index is ${currentIndex.value}")
    }
}

fun isInside(point: Offset, offsets: List<Rect>, margin: Float = 0.1f): Int {
    var minDistance = Float.MAX_VALUE
    var minIndex = 0
    for ((index, offset) in offsets.withIndex()) {
        val distanceX = Math.abs(point.x - offset.center.x)
        val distanceY = Math.abs(point.y - offset.center.y)
        if (distanceX < minDistance && distanceY < minDistance &&
            point.x in offset.left..offset.right &&
            point.y in offset.top..offset.bottom) {
            minDistance = distanceX.coerceAtMost(distanceY)
            minIndex = index
        }
    }
    return minIndex
}

fun getCenterOffset(point: Offset, offsets: List<Rect>, margin: Float = 0.1f): Offset {
    var minDistance = Float.MAX_VALUE
    var closestOffset = Offset.Zero
    for (offset in offsets) {
        val distanceX = Math.abs(point.x - offset.center.x)
        val distanceY = Math.abs(point.y - offset.center.y)
        if (distanceX < minDistance && distanceY < minDistance &&
            point.x in offset.left..offset.right &&
            point.y in offset.top..offset.bottom) {
            minDistance = distanceX.coerceAtMost(distanceY)
            // Calculate the center of the box with 25.dp width and height
            val centerX = offset.center.x - 28.5f
            val centerY = offset.center.y - 28.5f
            closestOffset = Offset(centerX, centerY)
        }
    }
    return closestOffset
}






@Composable
fun App() {
    val lightColor = Color(0xFFDDB88C)
    val darkColor = Color(0xFFAA6A39)
    val chessPiece = remember { mutableStateOf(DraggablePiece(Pair(0, 0), "♜")) }
    val allowedPositions = listOf(Pair(1, 6), Pair(2, 5), Pair(3, 4), Pair(1, 4), Pair(0, 5)) // b7, c6, d5, b5, a6

    Column(Modifier.fillMaxSize()) {
        for (row in 0 until 8) {
            Row {
                for (col in 0 until 8) {
                    Box(contentAlignment = Alignment.Center) {
                        val boxModifier = if (chessPiece.value.position == Pair(row, col)) {
                            Modifier
                                .size(50.dp)
                                .background(if ((row + col) % 2 == 0) lightColor else darkColor)

                        } else {
                            Modifier
                                .size(50.dp)
                                .background(if ((row + col) % 2 == 0) lightColor else darkColor)
                        }

                        Box(boxModifier, contentAlignment = Alignment.Center) {
                            Text(
                                text = "${'A' + col}${8 - row}",
                                color = if ((row + col) % 2 == 0) Color.Black else Color.White
                            )
                        }
                        var offsetX by remember { mutableStateOf(0f) }
                        var offsetY by remember { mutableStateOf(0f) }
                        Box(
                            modifier = Modifier
                                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                                .size(25.dp)
                                .background(Color.Red)
                                .clip(RoundedCornerShape(100))
                                .pointerInput(Unit) {
                                    detectTapGestures {  }
                                }
                                .pointerInput(Unit) {
                                    detectDragGestures { change, dragAmount ->
                                        change.consume()
                                        offsetX += dragAmount.x
                                        offsetY += dragAmount.y
                                    }
                                }
                        )
                    }
                }
            }
        }
    }
}

data class DraggablePiece(var position: Pair<Int, Int>, val piece: String)


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DamHajiTheme {
        App()
    }
}
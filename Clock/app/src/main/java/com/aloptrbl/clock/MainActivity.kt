package com.aloptrbl.clock

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColor
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aloptrbl.clock.model.Screen
import com.aloptrbl.clock.ui.components.BottomTabBar
import com.aloptrbl.clock.ui.screens.Alarm.AlarmScreen
import com.aloptrbl.clock.ui.screens.Clock.ClockScreen
import com.aloptrbl.clock.ui.screens.StopWatch.StopWatchScreen
import com.aloptrbl.clock.ui.screens.Timer.TimerScreen
import com.aloptrbl.clock.ui.theme.ClockTheme
import com.aloptrbl.clock.utils.PolarToCartesian
import com.aloptrbl.clock.utils.getOffsetCoordinates
import com.aloptrbl.clock.utils.innerShadow
import com.aloptrbl.clock.utils.toRadians
import com.aloptrbl.clock.utils.toRoman
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClockTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Row(Modifier.fillMaxWidth()) {
                        GlowingBox()
                    }

                }
            }
        }
    }
}


@Composable
fun App() {
    var coordinate by remember {mutableStateOf<Pair<Double, Double>>(Pair(0.0,0.0))}
    var coordinateMove by remember {mutableStateOf(Pair(0.0,0.0))}
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

    Column(Modifier.fillMaxSize().background(Color.White), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        // Clock version 1.0
        Box(contentAlignment = Alignment.Center, modifier = Modifier.clip(RoundedCornerShape(150.dp)).size(300.dp).background(Color.White).pointerInput(Unit)  {
            awaitEachGesture {
                val event = awaitPointerEvent()
                val current = event.changes.firstOrNull()
                coordinateMove = Pair(current?.position?.x?.toDouble(), current?.position?.y?.toDouble()) as Pair<Double, Double>
            }
        }) {
            for (i in 0 until 24) {
                val hour = if (i % 12 == 0) 12 else i % 12
                val degree = hour * 30.0

                val coordinate90 = getOffsetCoordinates(120.0,degree)
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
                        drawLine(Color.Black, center, Offset(center.x + 150f * cos(hourAngle.value.toRadians()), center.y + 150f * sin(hourAngle.value.toRadians())), strokeWidth = 8f)
                        drawLine(Color.Black, center, Offset(center.x + 170f * cos(minuteAngle.value.toRadians()), center.y + 170f * sin(minuteAngle.value.toRadians())), strokeWidth = 4f)
                        drawLine(Color.Red, center, Offset(center.x + 190f * cos(secondAngle.value.toRadians()), center.y + 190f * sin(secondAngle.value.toRadians())), strokeWidth = 2f)
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
        Spacer(modifier = Modifier.height(10.dp))

        // Clock version 2.0
        Box(contentAlignment = Alignment.Center, modifier = Modifier.clip(RoundedCornerShape(150.dp)).size(200.dp).background(Color.White).innerShadow(
            blur = 2.dp,
            color = Color.Black.copy(0.8f),
            cornersRadius = 100.dp,
            offsetX = (0).dp,
            offsetY = (0).dp
        ).pointerInput(Unit)  {
            awaitEachGesture {
                val event = awaitPointerEvent()
                val current = event.changes.firstOrNull()
                coordinateMove = Pair(current?.position?.x?.toDouble(), current?.position?.y?.toDouble()) as Pair<Double, Double>
            }
        }) {
            for (i in 0 until 24) {
                val hour = i % 12
                val degree = hour * 30.0

                val coordinateXY = getOffsetCoordinates(80.0,degree)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(20.dp)
                        .offset(coordinateXY.first.dp, coordinateXY.second.dp)
                        .clip(RoundedCornerShape(100))
                        .background(Color.Unspecified)

                ) {
                    Text("${hour}")
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Box(modifier = Modifier.size(300.dp), contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawLine(Color.Black, center, Offset(center.x + 50f * cos(hourAngle.value.toRadians()), center.y + 50f * sin(hourAngle.value.toRadians())), strokeWidth = 8f)
                        drawLine(Color.Black, center, Offset(center.x + 70f * cos(minuteAngle.value.toRadians()), center.y + 70f * sin(minuteAngle.value.toRadians())), strokeWidth = 4f)
                        drawLine(Color.Red, center, Offset(center.x + 90f * cos(secondAngle.value.toRadians()), center.y + 90f * sin(secondAngle.value.toRadians())), strokeWidth = 2f)
                    }
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Text(text = time.format(DateTimeFormatter.ofPattern("h:mm:ss a")))
        }
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black.copy(alpha = alpha))) {
                    append("o")
                }
                append("Hell")
            },
            fontSize = 30.sp
        )
        Row {
            time.format(DateTimeFormatter.ofPattern("h:mm:ss a")).forEachIndexed { index, char ->
                val color by transition.animateColor(
                    initialValue = Color.Black,
                    targetValue = Color.Red,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )
                Text(
                    text = char.toString(),
                    color = color,
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
            }
        }

        Row {
            time.format(DateTimeFormatter.ofPattern("h:mm:ss a")).forEachIndexed { index, char ->
                val color by transition.animateColor(
                    initialValue = Color.Black,
                    targetValue = Color.Red,
                    animationSpec = infiniteRepeatable(
                        animation = tween(400, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )
                if (oldTime.getOrNull(index) != char) {
                    Text(text = char.toString(),color = color)
                } else {
                    Text(char.toString())
                }
            }
        }
        oldTime = time.format(DateTimeFormatter.ofPattern("h:mm:ss a"))
    }
    }

@Composable
fun AppTest() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val items = listOf(
        Screen.Clock,
        Screen.Alarm,
        Screen.StopWatch,
        Screen.Timer,
    )

    Scaffold(
        bottomBar = { BottomTabBar(navController, items)}
    )  { innerPadding ->
        NavHost(navController = navController, startDestination = Screen.Clock.route, enterTransition = {  -> EnterTransition.None }, exitTransition = {  -> ExitTransition.None },  modifier = Modifier.padding(innerPadding)) {
            composable(Screen.Clock.route) { ClockScreen(navController) }
            composable(Screen.Alarm.route) { AlarmScreen(navController) }
            composable(Screen.StopWatch.route) { StopWatchScreen(navController) }
            composable(Screen.Timer.route) { TimerScreen(navController) }
        }
    }

}

@Composable
fun GlowingBox() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color.White, Color(0xFF00FF00)),
                    center = androidx.compose.ui.geometry.Offset(50f, 50f),
                    radius = 50f,
                    tileMode = TileMode.Clamp
                ),
                shape = RoundedCornerShape(0.dp)
            )
            .graphicsLayer(
                rotationZ = 4f,
                shadowElevation = 4f
            )
    ) {
        // Content of the box
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClockTheme {
        App()
    }
}
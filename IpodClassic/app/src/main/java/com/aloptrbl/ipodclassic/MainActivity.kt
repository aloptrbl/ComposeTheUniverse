package com.aloptrbl.ipodclassic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aloptrbl.ipodclassic.ui.theme.IpodClassicTheme
import java.lang.Math.atan2
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var rotation by remember { mutableStateOf(0f) }
            var previous by remember { mutableStateOf(0f) }
            var current by remember { mutableStateOf(0f) }

            IpodClassicTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Box(
                            Modifier
                                .size(300.dp)
                                .clip(RoundedCornerShape(300.dp))
                                .background(Color.Gray.copy(0.5F)).pointerInput(Unit) { // modifier to detect raw pointer input events. This allows you to handle the rotation gesture manually
                                    awaitEachGesture { // track the movement of the pointer. Start by waiting for the first down event, then enter a loop where you wait for the next pointer event and get the first change
                                            val down = awaitFirstDown()
                                            var previous = down
                                            while (true) {
                                                val event = awaitPointerEvent()
                                                val current = event.changes.firstOrNull()
                                                if (current == null || current.position == previous.position) {
                                                    previous = current ?: previous
                                                    continue
                                                }
                                                // Calculate rotation in degrees
                                                val angleCurrent = atan2(
                                                    current.position.y - size.height / 2,
                                                    current.position.x - size.width / 2
                                                )
                                                val anglePrevious = atan2(
                                                    previous.position.y - size.height / 2,
                                                    previous.position.x - size.width / 2
                                                )
                                                var angleDiff = angleCurrent - anglePrevious

                                                // Adjust the angle difference for wrap-around
                                                if (angleDiff > PI) {
                                                    angleDiff -= 2 * PI.toFloat()
                                                } else if (angleDiff < -PI) {
                                                    angleDiff += 2 * PI.toFloat()
                                                }

                                                rotation += angleDiff * 180f / PI.toFloat()
                                                rotation %= 360
                                                if (rotation < 0) {
                                                    rotation += 360
                                                }
                                                previous = current
                                            }
                                        }
                                }
                                .graphicsLayer(rotationZ = rotation)) {

                            Box(modifier = Modifier.size(25.dp).background(Color.Red))

                            val density = LocalDensity.current.density
                            val radius = 300 * density // Set the radius of the circular pattern

                            for (degree in 0 until 360 step 30) {
                                val angle = degree * (PI / 180)
                                val x = (radius * cos(angle)).toFloat()
                                val y = (radius * sin(angle)).toFloat()

                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(Color.Blue)
                                        .offset(x.dp, y.dp)
                                        .clip(RoundedCornerShape(15.dp))
                                )
                            }
                        }
                        Text("rotation $rotation")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        color = Color.Red,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IpodClassicTheme {
        Greeting("Android")
    }
}
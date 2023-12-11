package com.aloptrbl.imagegraphic

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.aloptrbl.imagegraphic.ui.theme.ImageGraphicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scrollState = rememberScrollState()
            ImageGraphicTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(Modifier.verticalScroll(scrollState)) {
                        Spacer(Modifier.height(35.dp))
                        ModifierGraphicsLayerModifierScale()
                        Spacer(Modifier.height(35.dp))
                        ModifierGraphicsLayerModifierTranslation()
                        Spacer(Modifier.height(35.dp))
                        ModifierGraphicsLayerModifierRotate()
                        Spacer(Modifier.height(35.dp))
                        CompositingStratgey_ModulateAlpha()
                        Spacer(Modifier.height(35.dp))
                        ModifierGraphicsLayerModifierBlendMode()
                        Spacer(Modifier.height(35.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ModifierGraphicsLayerModifierScale() {
    var scaleX by remember { mutableStateOf(1f) }
    var scaleY by remember { mutableStateOf(1f) }
    Column(Modifier.fillMaxWidth()) {
        BoxWithConstraints(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            val halfWidth = maxWidth / 2
            Image(
                painter = painterResource(id = R.drawable.sample),
                contentDescription = "Nature",
                modifier = Modifier
                    .padding(10.dp)
                    .width(100.dp)
                    .height(50.dp)
                    .graphicsLayer {
                        this.scaleX += scaleX
                        this.scaleY += scaleY
                        this.translationX += translationX
                        this.translationY += translationY
                        this.rotationX += rotationX
                        this.rotationY += rotationY
                        this.rotationZ += rotationZ
                    }
            )
        }
        Text(text = "Scale X")
        Slider(
            value = scaleX,
            onValueChange = { scaleX = it },
            valueRange = 0f..2f
        )

        Text(text = "Scale Y")
        Slider(
            value = scaleY,
            onValueChange = { scaleY = it },
            valueRange = 0f..2f
        )
    }
}

@Composable
fun ModifierGraphicsLayerModifierTranslation() {
    var translationX by remember { mutableStateOf(0f) }
    var translationY by remember { mutableStateOf(0f) }
    Column(Modifier.fillMaxWidth()) {
        BoxWithConstraints(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            val halfWidth = maxWidth / 2
            Image(
                painter = painterResource(id = R.drawable.sample),
                contentDescription = "Nature",
                modifier = Modifier
                    .padding(10.dp)
                    .width(100.dp)
                    .height(50.dp)
                    .graphicsLayer {
                        this.translationX += translationX
                        this.translationY += translationY
                    }
            )
        }
        Text(text = "Translation X")
        Slider(
            value = translationX,
            onValueChange = { translationX = it },
            valueRange = 0f..200f
        )

        Text(text = "Translation Y")
        Slider(
            value = translationY,
            onValueChange = { translationY = it },
            valueRange = 0f..200f
        )
    }
}

@Composable
fun ModifierGraphicsLayerModifierRotate() {
    var rotationX by remember { mutableStateOf(0f) }
    var rotationY by remember { mutableStateOf(0f) }
    var rotationZ by remember { mutableStateOf(0f) }
    Column(Modifier.fillMaxWidth()) {
        BoxWithConstraints(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            val halfWidth = maxWidth / 2
            Image(
                painter = painterResource(id = R.drawable.sample),
                contentDescription = "Nature",
                modifier = Modifier
                    .padding(10.dp)
                    .width(100.dp)
                    .height(50.dp)
                    .graphicsLayer {
                        this.transformOrigin = TransformOrigin(0f, 0f)
                        this.rotationX += rotationX
                        this.rotationY += rotationY
                        this.rotationZ += rotationZ
                    }
            )
        }

        Text(text = "Rotation X")
        Slider(
            value = rotationX,
            onValueChange = { rotationX = it },
            valueRange = 0f..360f
        )

        Text(text = "Rotation Y")
        Slider(
            value = rotationY,
            onValueChange = { rotationY = it },
            valueRange = 0f..360f
        )

        Text(text = "Rotation Z")
        Slider(
            value = rotationZ,
            onValueChange = { rotationZ = it },
            valueRange = 0f..360f
        )
    }
}

@Composable
fun CompositingStratgey_ModulateAlpha() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Base drawing, no alpha applied
        Canvas(
            modifier = Modifier.size(200.dp)
        ) {
            drawSquares()
        }

        Spacer(modifier = Modifier.size(36.dp))

        // Alpha 0.5f applied to whole composable
        Canvas(modifier = Modifier
            .size(200.dp)
            .graphicsLayer {
                alpha = 0.5f
            }) {
            drawSquares()
        }
        Spacer(modifier = Modifier.size(36.dp))

        // 0.75f alpha applied to each draw call when using ModulateAlpha
        Canvas(modifier = Modifier
            .size(200.dp)
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.ModulateAlpha
                alpha = 0.75f
            }) {
            drawSquares()
        }
    }
}

@Composable
fun ModifierGraphicsLayerModifierBlendMode() {
    var selectedOption by remember { mutableStateOf(RadioButtonOptions.OPTION_1) }
    val options = RadioButtonOptions.values()
    var blendMode by remember { mutableStateOf(BlendMode.DstIn) }
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Canvas(modifier = Modifier.size(200.dp)) {
            val size = Size(100.dp.toPx(), 100.dp.toPx())
            drawRect(color = Red, size = size)
            drawRect(
                color = Purple, size = size,
                topLeft = Offset(size.width / 4f, size.height / 4f),
                blendMode = blendMode
            )
        }
        Column {
            options.forEach { option ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (option == selectedOption),
                            onClick = { selectedOption = option }
                        )
                        .padding(5.dp)
                ) {
                    RadioButton(
                        selected = (option == selectedOption),
                        onClick = { selectedOption = option
                        blendMode = option.blendMode
                        }
                    )
                    Text(
                        text = option.blendMode.toString(),
                        style = MaterialTheme.typography.bodyMedium.merge(),
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        }

    }
}

private fun DrawScope.drawSquares() {

    val size = Size(100.dp.toPx(), 100.dp.toPx())
    drawRect(color = Red, size = size)
    drawRect(
        color = Purple, size = size,
        topLeft = Offset(size.width / 4f, size.height / 4f),
    )
    drawRect(
        color = Yellow, size = size,
        topLeft = Offset(size.width / 4f * 2f, size.height / 4f * 2f)
    )
}

val Purple = Color(0xFF7E57C2)
val Yellow = Color(0xFFFFCA28)
val Red = Color(0xFFEF5350)

enum class RadioButtonOptions(val blendMode: BlendMode) {
    OPTION_1(BlendMode.DstIn),
    OPTION_2(BlendMode.SrcIn),
    OPTION_3(BlendMode.SrcOver),
    OPTION_4(BlendMode.ColorBurn),
    OPTION_5(BlendMode.Lighten),
    OPTION_6(BlendMode.Color),
    OPTION_7(BlendMode.Clear),
    OPTION_8(BlendMode.ColorDodge),
    OPTION_9(BlendMode.Darken),
    OPTION_10(BlendMode.Difference),
    OPTION_11(BlendMode.Dst),
    OPTION_12(BlendMode.DstAtop),
    OPTION_13(BlendMode.DstOut),
    OPTION_14(BlendMode.DstOver),
    OPTION_15(BlendMode.Exclusion),
    OPTION_16(BlendMode.Hardlight),
    OPTION_17(BlendMode.Hue),
    OPTION_18(BlendMode.Luminosity),
    OPTION_19(BlendMode.Modulate),
    OPTION_20(BlendMode.Multiply),
    OPTION_21(BlendMode.Overlay),
    OPTION_22(BlendMode.Plus),
    OPTION_23(BlendMode.Saturation),
    OPTION_24(BlendMode.Screen),
    OPTION_25(BlendMode.Softlight),
    OPTION_26(BlendMode.Src),
    OPTION_27(BlendMode.SrcAtop),
    OPTION_28(BlendMode.SrcOut),
    OPTION_29(BlendMode.Xor),
}
package com.aloptrbl.agslshaderexperimentapp.ui.screens

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.aloptrbl.agslshaderexperimentapp.R

const val SHADER_SRC = """
   uniform shader composable;
   uniform float2 size;
   uniform float amount;
   
   half4 main(float2 fragCoord) {
   // manipulate color bbra, bbba, bbga etc.
   return composable.eval(fragCoord).bbra;
   }
   
"""

const val SHADER2_SRC = """
   uniform shader composable;
   uniform float2 size;
   uniform float amount;
   
   half4 main(float2 fragCoord) {
   half4 color = composable.eval(fragCoord);
   color.rgb = half3(dot(color.rgb, half3(8.2126, 0.7152, 0.0722)));
   return color;
   }
   
"""

const val SHADER3_SRC = """
   uniform shader composable;
   uniform float2 size;
   uniform float amount;
   
   half4 main(float2 fragCoord) {
   float distance = length(fragCoord - size * 0.5);
   half4 color = composable.eval(fragCoord);
   return half4(distance / max(size.x, size.y), 0.0,0.0,1.0);
   }
"""

const val SHADER4_SRC = """
   uniform shader composable;
   uniform float2 size;
   uniform float amount;
   
   half4 main(float2 fragCoord) {
   return half4(amount/ 150.0, 0.0,0.0,1.0);
   }
"""

@Composable
fun ScreenFour(resources: Resources) {
    val scrollState = rememberScrollState()
    val photo = BitmapFactory.decodeResource(resources,R.drawable.sample)
    var shader = RuntimeShader(SHADER_SRC)
    var shader2 = RuntimeShader(SHADER2_SRC)
    var shader3 = RuntimeShader(SHADER3_SRC)
    var shader4 = RuntimeShader(SHADER4_SRC)
    Column(verticalArrangement = Arrangement.Top, modifier = Modifier.verticalScroll(scrollState)) {
        val chromaticAmount = remember { mutableStateOf(100.0f) }
        // Image #1
        Image(bitmap = photo.asImageBitmap(), contentDescription = "", modifier = Modifier.size(250.dp).onSizeChanged {
            shader.setFloatUniform(
                "size",
                it.width.toFloat(),
                it.height.toFloat()

            )
        }.graphicsLayer {
            clip = true
            renderEffect = RenderEffect.createRuntimeShaderEffect(shader, "composable").asComposeRenderEffect()
        })

        // Image #2
        Image(bitmap = photo.asImageBitmap(), contentDescription = "", modifier = Modifier.size(250.dp).onSizeChanged {
            shader2.setFloatUniform(
                "size",
                it.width.toFloat(),
                it.height.toFloat()

            )
        }.graphicsLayer {
            clip = true
            renderEffect = RenderEffect.createRuntimeShaderEffect(shader2, "composable").asComposeRenderEffect()
        })

        // Image #3
        Image(bitmap = photo.asImageBitmap(), contentDescription = "", modifier = Modifier.size(250.dp).onSizeChanged {
            shader3.setFloatUniform(
                "size",
                it.width.toFloat(),
                it.height.toFloat()

            )
        }.graphicsLayer {
            clip = true
            renderEffect = RenderEffect.createRuntimeShaderEffect(shader3, "composable").asComposeRenderEffect()
        })

        // Image #4
        Image(bitmap = photo.asImageBitmap(), contentDescription = "", modifier = Modifier.size(250.dp).onSizeChanged {
            shader4.setFloatUniform(
                "size",
                it.width.toFloat(),
                it.height.toFloat()
            )
        }.graphicsLayer {
            clip = true
            shader4.setFloatUniform("amount", chromaticAmount.value)
            renderEffect = RenderEffect.createRuntimeShaderEffect(shader4, "composable").asComposeRenderEffect()
        })

        Slider(
            modifier = Modifier.padding(16.dp),
            value = chromaticAmount.value,
            onValueChange = {value-> chromaticAmount.value = value},
            valueRange =  1.0f..150f
        )

    }
}
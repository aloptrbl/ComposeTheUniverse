package com.aloptrbl.agslshaderexperimentapp.ui.screens

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.aloptrbl.agslshaderexperimentapp.R
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

const val SHADER_SOURCE = """
   uniform shader composable;
   uniform float2 resolution;
   uniform float time;
   
   half4 main(float2 fragCoord) {
   float2 uv = fragCoord / resolution.xy;
   half3 col = 0.5 + 0.5*cos(time+half3(uv.x, uv.y, uv.x)+half3(0,2,4));

   return half4(col,1.0);
   }
   
"""
@Composable
fun ScreenSix(resources: Resources) {
    val scrollState = rememberScrollState()
    var time by remember { mutableStateOf(0.0f) }
    val photo = BitmapFactory.decodeResource(resources, R.drawable.sample)
    var shader = RuntimeShader(SHADER_SOURCE)

    LaunchedEffect(Unit) {
        while (true) {
            time += 0.01f // Update time value
            delay(16L) // Delay to match approximately 60 frames per second
        }
    }

    Column(verticalArrangement = Arrangement.Top, modifier = Modifier.verticalScroll(scrollState)) {
        // Image #1
        Image(bitmap = photo.asImageBitmap(), contentDescription = "", modifier = Modifier.size(250.dp).onSizeChanged {
            shader.setFloatUniform(
                "resolution",
                it.width.toFloat(),
                it.height.toFloat()
            )
        }.graphicsLayer {
            clip = true
            shader.setFloatUniform("time", time)
            renderEffect = RenderEffect.createRuntimeShaderEffect(shader, "composable").asComposeRenderEffect()
        })
    }
}
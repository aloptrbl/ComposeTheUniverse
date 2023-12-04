package com.aloptrbl.agslshaderexperimentapp.ui.screens

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScreenTwo(shader: RuntimeShader, shader2: RuntimeShader, shader3: RuntimeShader) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        Text("Basic", fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier =  Modifier.padding(25.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(10.dp)
                .graphicsLayer {
                    renderEffect = RenderEffect
                        .createShaderEffect(shader)
                        .asComposeRenderEffect()
                }) {
            Text("Hello")
        }
        Text(" half4 main(float2 fragCoord) {\n" +
                "return half4(1.0, 0.0, 0.0, 1);\n" +
                " } ", color = Color.Black, fontStyle = FontStyle.Italic
        )
        Text("Box 1 color red because 1.0 is red. (1.0, 0.0, 0.0, 1) is RGBA.")
        Spacer(Modifier.height(10.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(10.dp)
                .graphicsLayer {
                    renderEffect = RenderEffect
                        .createShaderEffect(shader2)
                        .asComposeRenderEffect()
                }) {
            Text("Hello")
        }
        Text(" half4 main(float2 fragCoord) {\n" +
                "return half4(0.0, 1.0, 0.0, 1);\n" +
                " } ", color = Color.Black, fontStyle = FontStyle.Italic
        )
        Text("Box 2 color green because 1.0 is green. (0.0, 1.0, 0.0, 1) is RGBA.")
        Spacer(Modifier.height(10.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(10.dp)
                .graphicsLayer {
                    renderEffect = RenderEffect
                        .createShaderEffect(shader3)
                        .asComposeRenderEffect()
                }) {
            Text("Hello")
        }
        Text(" half4 main(float2 fragCoord) {\n" +
                "return half4(0.0, 0.0, 1.0, 1);\n" +
                " } ", color = Color.Black, fontStyle = FontStyle.Italic
        )
        Text("Box 3 color blue because 1.0 is blue. (0.0, 0.0, 1.0, 1) is RGBA.")
        Spacer(Modifier.height(10.dp))
    }
}
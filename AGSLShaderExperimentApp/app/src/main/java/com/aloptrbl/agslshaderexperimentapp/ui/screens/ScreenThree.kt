package com.aloptrbl.agslshaderexperimentapp.ui.screens

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun ScreenThree(shader: RuntimeShader) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        Text("Gradient", fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier =  Modifier.padding(25.dp))
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
        Text(" half4 main(float2 fragCoord) {   \n" +
                "\n" +
                "return half4(fragCoord.x / 1080, fragCoord.y / 2340, 0.75, 1);  \n" +
                "\n" +
                " } ", color = Color.Black, fontStyle = FontStyle.Italic
        )
        Text("In the context of the shader code half4(fragCoord.x/1080, fragCoord.y/2340, 1, 1), the color output will be determined by the position of the pixel (fragCoord) being processed:\n" +
                "\n" +
                "The red component (fragCoord.x/1080) will create a horizontal gradient from black (0) on the left to full red (1) on the right.\n" +
                "The green component (fragCoord.y/2340) will create a vertical gradient from black (0) at the bottom to full green (1) at the top.\n" +
                "The blue component is set to 1, which means full blue intensity across the entire gradient.\n" +
                "The alpha component is set to 1, which means the color is fully opaque across the entire gradient.\n" +
                "To easily determine the color of the gradient at any point, you can calculate the values of the red and green components based on the pixel’s coordinates. For example, a pixel located at the center of a 1080x2340 screen would have a color of half4(0.5, 0.5, 1, 1), which would be a shade of light blue because when red and green are mixed equally with full blue, they create a light blue color. The closer the pixel is to the top-right corner of the screen, the closer the color will be to white, as all color channels (red, green, and blue) approach their maximum value of 1. Conversely, the closer to the bottom-left corner, the color will be a solid blue, as the red and green channels approach 0.")
        Spacer(Modifier.height(10.dp))
    }
}
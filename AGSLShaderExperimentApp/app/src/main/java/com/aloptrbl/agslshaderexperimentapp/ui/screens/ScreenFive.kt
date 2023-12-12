package com.aloptrbl.agslshaderexperimentapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aloptrbl.agslshaderexperimentapp.R

@Composable
fun ScreenFive() {
    var scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        Text(text ="Recap", style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        Image(painter = painterResource(id = R.drawable.pixel), null, modifier = Modifier.size(200.dp))
        Text("const val SHADER3_SRC = \"\"\"\n" +
                "   uniform shader composable;\n" +
                "   uniform float2 size;\n" +
                "   uniform float amount;\n" +
                "   \n" +
                "   half4 main(float2 fragCoord) {\n" +
                "   float distance = length(fragCoord - size * 0.5);\n" +
                "   half4 color = composable.eval(fragCoord);\n" +
                "   return half4(distance / max(size.x, size.y), 0.0,0.0,1.0);\n" +
                "   }\n" +
                "\"\"\"")
        Spacer(Modifier.height(16.dp))
        Text("uniform shader composable; - This line declares a uniform variable named composable of type shader. Uniforms are variables that remain constant for all vertices or fragments during a single draw call1.\n" +
                "\n" +
                "uniform float2 size; - This line declares a uniform variable named size of type float2, which is a vector of two floats1. This could represent the size of the screen or some other 2D size.\n" +
                "\n" +
                "uniform float amount; - This line declares a uniform variable named amount of type float. This could be used to control some aspect of the shader’s behavior.\n" +
                "\n" +
                "half4 main(float2 fragCoord) { ... } - This is the main function of the shader. It takes a float2 input named fragCoord, which represents the coordinates of the current fragment (or pixel), and returns a half4, which is a vector of four half-precision floating point numbers1. This vector represents the color of the pixel in RGBA format.\n" +
                "\n" +
                "float distance = length(fragCoord - size * 0.5); - This line calculates the distance from the current fragment to the center of the size rectangle and stores it in the distance variable.\n" +
                "\n" +
                "half4 color = composable.eval(fragCoord); - This line evaluates the composable shader at the current fragment coordinates and stores the resulting color in the color variable.\n" +
                "\n" +
                "return half4(distance / max(size.x, size.y), 0.0,0.0,1.0); - This line returns a color whose red component is proportional to the distance from the center of the size rectangle, and whose green, blue, and alpha components are fixed.")
        Spacer(Modifier.height(16.dp))
        Text("fragCoord: Imagine you’re painting a picture, and each tiny dot you paint on the canvas is a pixel. Now, each of these pixels has a specific position on the canvas, right? That position is what fragCoord represents in our shader program. It’s a pair of numbers (x, y) that tells us the exact location of a pixel on the screen.\n" +
                "\n" +
                "half4: Now, think about when you mix colors for your painting. You usually mix different amounts of primary colors - red, green, and blue (RGB) - to get the color you want. In computer graphics, we do the same thing but we also add a fourth component, alpha, which represents transparency. \n So, half4 is just a fancy way of saying we have four half-precision numbers (smaller numbers that take up less memory in the computer), which represent the red, green, blue, and alpha (RGBA) components of a color.")
        Spacer(Modifier.height(16.dp))
        Text("fragCoord is a built-in variable that represents the position of a single pixel in the framebuffer. It is a vec2 that ranges from 0.5 to resolution-0.5 in both x and y directions, where resolution is the size of the rendering surface1. For example, if the resolution is 640x360, then fragCoord.x will vary from 0.5 to 639.5 and fragCoord.y will vary from 0.5 to 359.5. Each pixel has a unique fragCoord value that corresponds to its location on the screen.")
        Spacer(Modifier.height(16.dp))
        Text("The main function is executed for each and every pixel (or fragment) that’s being rendered. So if you’re rendering an image that’s 800x600 pixels, the main function will be run 480,000 times - once for each pixel. Each time it runs, fragCoord will contain the coordinates of the current pixel. This is how shaders can apply complex effects to images, by calculating the color of each pixel individually")
    }
}
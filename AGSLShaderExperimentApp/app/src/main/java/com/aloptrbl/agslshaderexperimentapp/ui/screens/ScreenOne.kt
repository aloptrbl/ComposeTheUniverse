package com.aloptrbl.agslshaderexperimentapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScreenOne() {
    val scrollState = rememberScrollState()
    Column(Modifier.fillMaxSize().verticalScroll(scrollState)) {
        Text("Introduction", fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier =  Modifier.padding(25.dp))
        Text("AGSL, or Android Graphics Shading Language, is like a magic wand for your Android device’s screen. Imagine you’re a painter, and your phone’s screen is your canvas. AGSL is the set of brushes and colors you use to create beautiful pictures, animations, and effects that make the apps and games look fantastic.")
        Text("It tells the graphics processor (that’s like the brain for images on your phone) exactly how to draw each pixel. So whether it’s a smooth gradient, a shiny surface, or a shadow that looks just right, AGSL is the artist’s tool that makes it all happen.")
        Spacer(Modifier.height(10.dp))
        Text("Starting with AGSL might seem a bit complex, like learning to mix colors for the first time, but once you get the hang of it, you can create amazing things. It’s a language that lets you write instructions for the graphics processor, and these instructions are called “shaders.”")
        Text("Shaders are small programs that can do a lot of cool stuff. They can make objects look three-dimensional, create realistic lighting, or even make things move. And the best part? You can see the results of your work right away on the screen.")
        Spacer(Modifier.height(10.dp))
        Text("Shaders determine how the pixels on the screen are drawn and what color they should be, which affects everything you see in a digital image.")
        Text("There are different types of shaders, each responsible for a different part of the rendering process:")
        Spacer(Modifier.height(10.dp))
        Text("1. Vertex Shaders: These process the vertices of 3D models. They handle the transformation and lighting calculations that determine the position of vertices in the scene.\n" +
                "\n" +
                "2. Fragment Shaders (or Pixel Shaders): These calculate the color of individual pixels by considering lights, shadows, and textures. They are responsible for the final look of the surfaces in the scene.\n" +
                "\n" +
                "3. Geometry Shaders: These can create new geometry on the fly. They can take primitives like points, lines, and triangles as input and output a different set of primitives.\n" +
                "\n" +
                "4. Tessellation Shaders: These control the tessellation process, which subdivides shapes into finer detail. This is useful for creating more complex geometry from simpler shapes.\n" +
                "\n" +
                "5. Compute Shaders: These are used for general-purpose computing on the GPU. They can handle tasks not directly related to drawing pixels or vertices.")

    }
}
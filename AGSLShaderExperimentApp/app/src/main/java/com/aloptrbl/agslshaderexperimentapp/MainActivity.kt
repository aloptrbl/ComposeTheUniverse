package com.aloptrbl.agslshaderexperimentapp

import android.graphics.RuntimeShader
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.aloptrbl.agslshaderexperimentapp.ui.components.HorizontalTabView
import com.aloptrbl.agslshaderexperimentapp.ui.theme.AGSLShaderExperimentAppTheme

private const val SHADER_SRC = """
 half4 main(float2 fragCoord) {
return half4(1.0, 0.0, 0, 1);
 }
 """

private const val SHADER_SRC2 = """
 half4 main(float2 fragCoord) {
return half4(0.0, 1.0, 0, 1);
 }
 """

private const val SHADER_SRC3 = """
 half4 main(float2 fragCoord) {
return half4(0.0, 0.0, 1, 1);
 }
 """

private const val GRADIENT_SRC = """
    half4 main(float2 fragCoord) {
return half4(fragCoord.x/1080, fragCoord.y/2340, 1, 1);
 }
 """


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shader = RuntimeShader(SHADER_SRC)
        val shader2 = RuntimeShader(SHADER_SRC2)
        val shader3 = RuntimeShader(SHADER_SRC3)
        val gradientShader = RuntimeShader(GRADIENT_SRC)
        setContent {
            AGSLShaderExperimentAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                   HorizontalTabView(shader, shader2, shader3, gradientShader, this)
                }
            }
        }
    }
}
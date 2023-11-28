package com.aloptrbl.customtext

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloptrbl.customtext.ui.theme.CustomTextTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomTextTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CustomTextApp()
                }
            }
        }
    }
}

@Composable
fun CustomTextApp() {
    val firstColors = listOf(
        Color(red = 244, green = 7, blue = 82), // hsla(341, 94%, 49%, 1)
        Color(red = 249, green = 171, blue = 143) // hsla(16, 90%, 77%, 1)
    )

    val secondColors = listOf(
        Color(red = 67, green = 156, blue = 251), // hsla(211, 96%, 62%, 1)
        Color(red = 241, green = 135, blue = 251) // hsla(295, 94%, 76%, 1)
    )

    Column() {
        Box(
            Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = firstColors,
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = Float.POSITIVE_INFINITY, y = 0f)
                    )
                )) {
            Column(Modifier.padding(10.dp)) {
                OutlineText("2023")
                CustomFontText("Dive")
                AllPropText("Hello World")
                GradientText("Welcome")
                GradientTwoText("Jetpack Compose")
            }

        }
        Box(
            Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = secondColors,
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = Float.POSITIVE_INFINITY, y = 0f)
                    )
                )) {
            Column(Modifier.padding(10.dp).fillMaxSize(), horizontalAlignment = Alignment.End) {
                CustomFontTwoText("2024")
            }

        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun OutlineText(name: String) {
    Text(name, fontSize = 50.sp, color = Color.White,  style = TextStyle.Default.copy(
        drawStyle = Stroke(
            miter = 100f,
            width = 2.5f,
            join = StrokeJoin.Round
        )))
}

@SuppressLint("ResourceType")
@OptIn(ExperimentalTextApi::class)
@Composable
fun CustomFontText(name: String) {
    Text(name, fontSize = 30.sp, color = Color.White, fontFamily = FontFamily(
        Font(R.font.dotine, FontWeight.Light)
    ))
}

@SuppressLint("ResourceType")
@OptIn(ExperimentalTextApi::class)
@Composable
fun CustomFontTwoText(name: String) {
    Text(name, fontSize = 30.sp, color = Color.White, fontFamily = FontFamily(
        Font(R.font.koffins, FontWeight.Light)
    ))
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun AllPropText(name: String) {
    Text(name, fontSize = 20.sp, color = Color.White,fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        letterSpacing = 0.5.sp,
        textDecoration = TextDecoration.LineThrough,
        textAlign = TextAlign.Center,
        lineHeight = 28.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
        softWrap = true,
        onTextLayout = { textLayoutResult -> /* handle text layout result */ },
        style = LocalTextStyle.current.copy(color = Color.Red))
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun GradientText(name: String) {
    Text(name, fontSize = 20.sp, color = Color.White,fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.SansSerif,
        letterSpacing = 1.sp,
        textDecoration = TextDecoration.Underline,
        lineHeight = 28.sp,
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = listOf(Color.Magenta, Color.Cyan),
                start = Offset(0f, 200f),
                end = Offset(200f, 0f)
            )
        )
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun GradientTwoText(name: String) {
    Text(name, fontSize = 20.sp, color = Color.White,fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.W900,
        fontFamily = FontFamily.Monospace,
        lineHeight = 28.sp,
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = listOf(Color.Green, Color.Green, Color.White.copy(0.9F)),
                start = Offset(0f, 0f), // Top of the screen
                end = Offset(0f, Float.POSITIVE_INFINITY) // Bottom of the screen
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustomTextTheme {
        CustomTextApp()
    }
}
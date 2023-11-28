package com.aloptrbl.shineeffectapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloptrbl.shineeffectapp.ui.theme.ShineEffectAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShineEffectAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Shine Effect", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                        LoadImage()
                    }
                }
            }
        }
    }
}

@Composable
fun ShineEffect(): Brush {
    val gradient = listOf(
        Color.Transparent,
        Color.Transparent,
        Color.White.copy(alpha = 0.1f),
        Color.White.copy(alpha = 0.5f),
        Color.White.copy(alpha = 1f),
        Color.White.copy(alpha = 0.5f),
        Color.White.copy(alpha = 0.1f),
        Color.Transparent,
        Color.Transparent
    )

    val transition = rememberInfiniteTransition(label = "Shine Effect")

    val translateAnimation = transition.animateFloat( //animate the transition
        initialValue = 0f,
        targetValue = 1500f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, // duration for the animation
                easing = FastOutLinearInEasing
            )
        ), label = "Shine Effect"
    )

    return linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )
}

@Composable
fun LoadImage() {
        Box(Modifier.width(250.dp).height(450.dp)) {
            Image(
                painter = painterResource(id = R.drawable.cat),
                contentDescription = "cat",
                Modifier.clip(RoundedCornerShape(10.dp))

            )
            Box(modifier = Modifier
                .matchParentSize()
                .background(ShineEffect()))
        }
}

@Preview(showBackground = true)
@Composable
fun ShineEffectPreview() {
    ShineEffectAppTheme {
        LoadImage()
    }
}
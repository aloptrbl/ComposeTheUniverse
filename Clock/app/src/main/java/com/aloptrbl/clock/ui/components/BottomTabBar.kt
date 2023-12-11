package com.aloptrbl.clock.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.aloptrbl.clock.R
import com.aloptrbl.clock.model.Screen
import com.aloptrbl.clock.utils.GlowImage

@Composable
fun BottomTabBar(
    navController: NavHostController,
    items: List<Screen>,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val haptic = LocalHapticFeedback.current
    val context = LocalContext.current
    var selectedScreen by remember { mutableStateOf(0) }
    val paint = remember {
        Paint().apply {
            style = PaintingStyle.Stroke
            strokeWidth = 20f
        }
    }
    val frameworkPaint = remember { paint.asFrameworkPaint() }
    var pointerOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    val color = Color.Magenta
    Row(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (item in items) {
            val isSelected = item == items[selectedScreen]
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .weight(1F)
                    .align(Alignment.CenterVertically)
                    .background(Color(0xFF442342))
                    .clickable(onClick = {
                        haptic.performHapticFeedback(
                            HapticFeedbackType.LongPress
                        )
                        selectedScreen = items.indexOf(item)
                        navController.navigate(item.route)
                    }
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .height(IntrinsicSize.Max),
                ) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = "icon",
                        modifier = Modifier.size(35.dp).drawBehind {
                            val glowColor = Color(0xFFffd176)
                            val glowRadius = 100.dp.toPx()

                            // Create a radial gradient with the glow color fading to transparent
                            val brush = Brush.radialGradient(
                                colors = listOf(glowColor, glowColor.copy(alpha = 0.6f), Color.Transparent),
                                center = pointerOffset,
                                radius = glowRadius
                            )

                            // Draw the glowing border
                            drawRoundRect(
                                brush = brush,
                                size = Size(size.width - glowRadius, size.height - glowRadius),
                                cornerRadius = CornerRadius(x = 50f, y = 50f),
                                style = Stroke(width = glowRadius, pathEffect = PathEffect.cornerPathEffect(50f))
                            )
                        },
                    )
                }
            }
        }
    }
}
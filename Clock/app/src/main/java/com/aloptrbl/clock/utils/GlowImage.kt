package com.aloptrbl.clock.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GlowImage(image: ImageBitmap, radius: Dp) {
    val paint = remember {
        Paint().apply {
            style = PaintingStyle.Stroke
            strokeWidth = 20f
        }
    }
    val frameworkPaint = remember { paint.asFrameworkPaint() }
    val color = Color.Magenta

    Canvas(modifier = Modifier.size(image.width.dp, image.height.dp)) {
        drawIntoCanvas { canvas ->
            frameworkPaint.color = color.copy(alpha = 0f).toArgb()
            frameworkPaint.setShadowLayer(
                radius.toPx(), 0f, 0f, color.copy(alpha = .5f).toArgb()
            )
            canvas.drawImage(image, Offset.Zero, paint)
        }
    }
}

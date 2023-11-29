package com.aloptrbl.gradientgeneratorapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "gradient animation")

    val animationSpec by remember {
        mutableStateOf(
            infiniteRepeatable<Float>(
                animation = tween(durationMillis = 1000, easing = EaseInOutCubic),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    val offset by infiniteTransition.animateFloat(
        initialValue = 1500f,
        targetValue = 2500f,
        animationSpec = animationSpec, label = "animate Gradient"
    )

    var gradient = Brush.verticalGradient(
        colors = listOf(
            Color(android.graphics.Color.parseColor("#7ef29d")),
            Color(android.graphics.Color.parseColor("#0f68a9"))
        ),
        startY = 0f,
        endY = offset
    )

    var switchGradient by remember { mutableStateOf<Brush>(SolidColor(Color.Transparent)) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var dominanceColors by remember { mutableStateOf<List<Color>>(emptyList()) }
    val context = LocalContext.current
    var uploadViewAlpha by remember { mutableFloatStateOf(1f) }
    var resultViewAlpha by remember { mutableFloatStateOf(0f) }

    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current
    val clipboardManager = LocalClipboardManager.current

    Row(
        Modifier
            .background(
                brush = (switchGradient as? SolidColor)
                    ?.takeIf { it.value == Color.Transparent }
                    ?.let { gradient } ?: switchGradient)
            .fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
        Box {
            Box(
                Modifier
                    .alpha(uploadViewAlpha)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        text = "Extract gradient from an image and save them as color gradients.",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                    Image(
                        painterResource(id = R.drawable.upload), "",
                        Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ImagePicker { uri ->
                            imageUri = uri
                            uploadViewAlpha = 0f
                            resultViewAlpha = 1f
                            scope.launch {
                                var bmp = uriToBitmap(context, uri)
                                bmp?.let {
                                    val colorList = getDominantColor(it).distinct()
                                    colorList.forEach { color ->
                                        val hexColor = String.format("#%06X", (0xFFFFFF and color))
                                        dominanceColors += Color(
                                            android.graphics.Color.parseColor(
                                                hexColor
                                            )
                                        )
                                    }
                                    switchGradient = Brush.verticalGradient(
                                        colors = dominanceColors,
                                        startY = 0f,
                                        endY = offset
                                    )


                                }
                            }

                        }
                    }
                    Text(
                        text = "PNG, JPG smaller than 10 MB",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Box(
                Modifier
                    .alpha(resultViewAlpha)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(Modifier.padding(5.dp)) {
                    imageUri?.let { LoadImageFromUri(imageUri = it) }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        dominanceColors.forEach {
                            Box(
                                Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .size(40.dp)
                                    .padding(5.dp)
                                    .background(it)
                                    .combinedClickable(
                                        onClick = { /* Handle click */ },
                                        onLongClick = {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                            clipboardManager.setText(AnnotatedString(String.format("#%06X", it.toArgb() and 0xFFFFFF)))
                                            Toast.makeText(context, "Copied!", Toast.LENGTH_LONG).show()
                                        },
                                    )
                            )
                        }
                    }
                    Text(
                        text = "Long press to copy Hex color code",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun LoadImageFromUri(imageUri: Uri) {
    val painter = rememberAsyncImagePainter(model = imageUri)

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}

@Composable
fun ImagePicker(onImagePicked: (Uri) -> Unit) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                onImagePicked(it)
            }
        }

    Button(onClick = { launcher.launch("image/*") }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            Spacer(Modifier.width(2.dp)) // Space between icon and text
            Text("Upload")
        }
    }
}

fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    return context.contentResolver.openInputStream(uri)?.use { inputStream ->
        BitmapFactory.decodeStream(inputStream)
    }
}

fun getDominantColor(bitmap: Bitmap): List<Int> {
    var defaultColor = 0xFF00FF00.toInt()
    val palette = Palette.from(bitmap).generate()
    return listOf(
        palette.getVibrantColor(defaultColor),
        palette.getLightVibrantColor(defaultColor),
        palette.getDarkVibrantColor(defaultColor),
        palette.getMutedColor(defaultColor),
        palette.getLightMutedColor(defaultColor),
        palette.getDarkMutedColor(defaultColor)
    )
}

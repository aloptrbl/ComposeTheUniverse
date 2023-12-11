package com.aloptrbl.clock.ui

import android.appwidget.AppWidgetManager
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceComposable
import androidx.glance.GlanceId
import androidx.glance.LocalSize
import androidx.glance.appwidget.AndroidRemoteViews
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import com.aloptrbl.clock.R
import com.aloptrbl.clock.model.DigitalFaceWatch
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AnalogClockWidget: GlanceAppWidget() {

    companion object {
        private val SMALL_SQUARE = DpSize(130.dp, 102.dp)
        private val HORIZONTAL_RECTANGLE = DpSize(554.dp, 51.dp)
        private val BIG_SQUARE = DpSize(250.dp, 250.dp)
    }

    override val sizeMode = SizeMode.Responsive(
        setOf(
            SMALL_SQUARE,
            HORIZONTAL_RECTANGLE,
            BIG_SQUARE
        )
    )
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            ContentView(context)
        }
    }

    @Composable
    @GlanceComposable
    fun ContentView(context: Context) {
        val size = LocalSize.current
        val packageName = context.packageName
        var time by remember { mutableStateOf(LocalDateTime.now()) }
        val timeDisplay = time.format(DateTimeFormatter.ofPattern("hh:mm a"))
        LaunchedEffect(key1 = time) {
            while (true) {
                time = LocalDateTime.now()
                val appWidgetManager = AppWidgetManager.getInstance(context)
                AnalogClockWidget().updateAll(context)
                delay(1000)
            }
        }

        if(size.width <= HORIZONTAL_RECTANGLE.width) {
            var remoteViews = RemoteViews(packageName, R.layout.digital_clock__hibiscus_face_554x51)
            remoteViews.setImageViewResource(R.id.first_hour,setClockDigitImage(timeDisplay[0]))
            remoteViews.setImageViewResource(R.id.second_hour,setClockDigitImage(timeDisplay[1]))
            remoteViews.setImageViewResource(R.id.colon,setClockDigitImage(timeDisplay[2]))
            remoteViews.setImageViewResource(R.id.first_minute,setClockDigitImage(timeDisplay[3]))
            remoteViews.setImageViewResource(R.id.second_minute,setClockDigitImage(timeDisplay[4]))
            remoteViews.setImageViewResource(R.id.first_12h,setClockDigitImage(timeDisplay[6]))
            remoteViews.setImageViewResource(R.id.second_12h,setClockDigitImage(timeDisplay[7]))
            AndroidRemoteViews(remoteViews)
        }
        else if(size.width <= SMALL_SQUARE.width) {
            var remoteViews = RemoteViews(packageName, R.layout.digital_clock__hibiscus_face_130x102)
            remoteViews.setImageViewResource(R.id.first_hour,setClockDigitImage(timeDisplay[0]))
            remoteViews.setImageViewResource(R.id.second_hour,setClockDigitImage(timeDisplay[1]))
            remoteViews.setImageViewResource(R.id.colon,setClockDigitImage(timeDisplay[2]))
            remoteViews.setImageViewResource(R.id.first_minute,setClockDigitImage(timeDisplay[3]))
            remoteViews.setImageViewResource(R.id.second_minute,setClockDigitImage(timeDisplay[4]))
            remoteViews.setImageViewResource(R.id.first_12h,setClockDigitImage(timeDisplay[6]))
            remoteViews.setImageViewResource(R.id.second_12h,setClockDigitImage(timeDisplay[7]))
            AndroidRemoteViews(remoteViews)
        }
    }
}

fun setClockDigitImage(char: Char): Int {
    val digitImage = 0

    if(DigitalFaceWatch.HIBISCUS == DigitalFaceWatch.LAVENDER) {
        val digitImage =   when (char) {
            '1' ->  R.drawable.hibiscus_1
            '2' -> R.drawable.hibiscus_2
            '3' -> R.drawable.hibiscus_3
            '4' -> R.drawable.hibiscus_4
            '5' -> R.drawable.hibiscus_5
            '6' -> R.drawable.hibiscus_6
            '7' -> R.drawable.hibiscus_7
            '8' -> R.drawable.hibiscus_8
            '9' -> R.drawable.hibiscus_9
            '0' -> R.drawable.hibiscus_0
            ':' -> R.drawable.hibiscus_colon
            'a', 'A' -> R.drawable.hibiscus_a
            'p', 'P' -> R.drawable.hibiscus_p
            'm', 'M' -> R.drawable.hibiscus_m
            else -> 0
        }
        return digitImage
    } else if (DigitalFaceWatch.HIBISCUS == DigitalFaceWatch.HIBISCUS) {
        val digitImage =   when (char) {
            '1' ->  R.drawable.lavendar_1
            '2' -> R.drawable.lavendar_2
            '3' -> R.drawable.lavendar_3
            '4' -> R.drawable.lavendar_4
            '5' -> R.drawable.lavendar_5
            '6' -> R.drawable.lavendar_6
            '7' -> R.drawable.lavendar_7
            '8' -> R.drawable.lavendar_8
            '9' -> R.drawable.lavendar_9
            '0' -> R.drawable.lavendar_0
            ':' -> R.drawable.lavendar_colon
            'a', 'A' -> R.drawable.lavendar_a
            'p', 'P' -> R.drawable.lavendar_p
            'm', 'M' -> R.drawable.lavendar_m
            else -> 0
        }
        return digitImage
    }

    return digitImage
    }

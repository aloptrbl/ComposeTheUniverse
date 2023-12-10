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
import androidx.glance.GlanceComposable
import androidx.glance.GlanceId
import androidx.glance.appwidget.AndroidRemoteViews
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import com.aloptrbl.clock.R
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AnalogClockWidget: GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            ContentView(context)
        }
    }

    @Composable
    @GlanceComposable
    fun ContentView(context: Context) {
        val packageName = context.packageName
        var time by remember { mutableStateOf(LocalDateTime.now()) }
        val remoteViews = RemoteViews(packageName, R.layout.analog_clock_face)
        LaunchedEffect(key1 = time) {
            while (true) {
                time = LocalDateTime.now()
                val appWidgetManager = AppWidgetManager.getInstance(context)
                AnalogClockWidget().updateAll(context)
                delay(1000)
            }
        }

        val timeDisplay = time.format(DateTimeFormatter.ofPattern("hh:mm a"))
        Log.d("hour", timeDisplay[0].toString())
        Log.d("hour", timeDisplay[1].toString())
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

fun setClockDigitImage(char: Char): Int {
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
    }

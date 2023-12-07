package com.aloptrbl.clock.ui

import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceComposable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.background
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.aloptrbl.clock.ui.components.analogs.AnalogClockWithRomanNumeralsView
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DigitalClockWidget: GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
          ContentView()
        }
    }

    @Composable
    @GlanceComposable
    fun ContentView() {
        var time by remember { mutableStateOf(LocalDateTime.now()) }
        LaunchedEffect(key1 = time) {
            while (true) {
                time = LocalDateTime.now()
                delay(1000)
            }
        }
        Column(modifier = GlanceModifier.fillMaxSize().background(Color.White, Color.Black), horizontalAlignment = Alignment.CenterHorizontally, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = GlanceModifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                val time = LocalTime.now()
                Text(text = time.format(DateTimeFormatter.ofPattern("h:mm:ss a")), style = TextStyle(fontSize = 24.sp))
            }
        }
    }
}
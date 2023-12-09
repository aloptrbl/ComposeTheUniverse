package com.aloptrbl.clock.ui

import android.content.Context
import android.widget.RemoteViews
import androidx.glance.GlanceComposable
import androidx.glance.GlanceId
import androidx.glance.appwidget.AndroidRemoteViews
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent

class AnalogClockWidget: GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            ContentView(context)
        }
    }

    @GlanceComposable
    fun ContentView(context: Context) {
        val packageName = context.packageName
        AndroidRemoteViews(RemoteViews(packageName, R.layouts))
    }

}
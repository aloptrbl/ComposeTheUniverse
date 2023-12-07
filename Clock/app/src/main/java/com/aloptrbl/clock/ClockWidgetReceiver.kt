package com.aloptrbl.clock

import android.app.AlarmManager
import android.app.ForegroundServiceStartNotAllowedException
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.updateAll
import com.aloptrbl.clock.services.ClockWidgetService
import com.aloptrbl.clock.ui.DigitalClockWidget
import com.aloptrbl.clock.utils.PermissionUtils
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ClockWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = DigitalClockWidget()
    private val coroutineScope = MainScope()

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        context?.let { startService(it) }
        val intent = PendingIntent.getService(
            context,
            1522,
            Intent(context, ClockWidgetService::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        );
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime(),
            60000,
            intent
        )
    }

    private fun cancelWidgetProcess(context: Context) {
        try {
            context.stopService(Intent(context, ClockWidgetService::class.java))
        } catch (ignored: IllegalStateException) {
        }
        val intent = PendingIntent.getService(
            context,
            1522,
            Intent(context, ClockWidgetService::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        );
        (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).cancel(intent)
    }

    override fun onDisabled(context: Context?) {
        context?.let { cancelWidgetProcess(it) }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        context?.let { cancelWidgetProcess(it) }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == "UPDATE_ACTION") {
            coroutineScope.launch {
                glanceAppWidget.updateAll(context)
            }
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        startService(context)
    }

    private fun startService(context: Context) {
        if (PermissionUtils.isIgnoringBatteryOptimizations(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    try {
                        context.startForegroundService(
                            Intent(
                                context,
                                ClockWidgetService::class.java
                            )
                        )
                    } catch (e: ForegroundServiceStartNotAllowedException) {
                        e.printStackTrace()
                    }
                } else {
                    context.startForegroundService(
                        Intent(
                            context,
                            ClockWidgetService::class.java
                        )
                    )
                }
            } else {
                context.startService(Intent(context, ClockWidgetService::class.java))
            }
        }
    }
}
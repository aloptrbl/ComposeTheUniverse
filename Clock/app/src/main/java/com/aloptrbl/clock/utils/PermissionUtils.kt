package com.aloptrbl.clock.utils

import android.app.NotificationManager
import android.content.Context
import android.os.PowerManager

object PermissionUtils {
    fun isIgnoringBatteryOptimizations(context: Context): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }

    fun hasNotificationPermission(context: Context): Boolean {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager.areNotificationsEnabled()
    }
}
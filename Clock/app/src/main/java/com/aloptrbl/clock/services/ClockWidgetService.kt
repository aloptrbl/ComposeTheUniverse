package com.aloptrbl.clock.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ServiceInfo
import android.graphics.Color
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.Display
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.glance.appwidget.updateAll
import com.aloptrbl.clock.R
import com.aloptrbl.clock.ui.DigitalClockWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClockWidgetService: Service() {

    private lateinit var context: Context
    private var isVisbleScreen = true
    private val serviceChannelId = "widget_channel"
    private val serviceChannelName = "Widget Channel"

    private var broadcastReceiver: BroadcastReceiver? = null
    private val handler = Handler(Looper.getMainLooper())
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        
        isVisbleScreen = (getSystemService(Context.DISPLAY_SERVICE) as DisplayManager)
            .displays[0].state == Display.STATE_ON
        
        if(isVisbleScreen) {
            postCallbacks()
            setNotification()
        }
        
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                try {
                    when (intent.action) {
                        Intent.ACTION_SCREEN_ON -> {
                            isVisbleScreen = true
                            postCallbacks()
                            setNotification()
                            Log.d("ClockWidgetService", "Screen is on")
                        }

                        Intent.ACTION_SCREEN_OFF -> {
                            isVisbleScreen = false
                            // Leave the service running in the foreground
                            // because Android 14 is killing the service when
                            // the screen is off
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                                Log.d("ClockWidgetService", "Screen is off, we're on Android 14+")
                            } else {
                                stopForeground(STOP_FOREGROUND_REMOVE)
                            }
                            removeCallbacks()
                            Log.d("ClockWidgetService", "Screen is off")
                        }
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }
            }
        }

        registerReceiver(broadcastReceiver, filter)
    }

    override fun onDestroy() {
        removeCallbacks()
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }
    private fun postCallbacks() {
       removeCallbacks()
        handler.post(clockWidgetRunnable)
    }

    private fun removeCallbacks() {
        handler.removeCallbacks(clockWidgetRunnable)
        handler.removeCallbacksAndMessages(null)
    }

    private fun setNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationBuilder = NotificationCompat.Builder(applicationContext, createNotificationChannel())
            val notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setSubText(getString(R.string.app_name))
                .setContentText(getString(R.string.app_name))
                .build()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    startForeground(101, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
                } else {
                    startForeground(101, notification)
                }
            } else {
                startForeground(101, notification)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val channel = NotificationChannel(serviceChannelId, serviceChannelName, NotificationManager.IMPORTANCE_NONE)
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
        return serviceChannelId
    }

    private val clockWidgetRunnable = object : Runnable {
        override fun run() {
            CoroutineScope(Dispatchers.Default).launch {
                withContext(Dispatchers.Main) {
                    DigitalClockWidget().updateAll(context)
                }
            }
            handler.postDelayed(this, 1000L)
        }

    }
}
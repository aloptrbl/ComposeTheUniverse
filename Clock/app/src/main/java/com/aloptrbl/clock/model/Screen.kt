package com.aloptrbl.clock.model

import com.aloptrbl.clock.R

sealed class Screen(val route: String, val icon: Int, val label: String) {
    object Clock : Screen("clock", R.drawable.clock, "Clock")
    object Alarm : Screen("alarm", R.drawable.alarm, "Alarm")
    object StopWatch : Screen("stopwatch", R.drawable.stopwatch, "Stop Watch")
    object Timer : Screen("timer", R.drawable.alarm, "Timer")
}
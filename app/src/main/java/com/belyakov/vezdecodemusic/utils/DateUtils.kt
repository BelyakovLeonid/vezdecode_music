package com.belyakov.vezdecodemusic.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun Calendar.toTimeString(): String {
    val simpleDateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
    return simpleDateFormat.format(this.time)
}

fun Long.toTimeString(): String {
    val simpleDateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
    return simpleDateFormat.format(
        Date().apply { time = this@toTimeString }
    )
}
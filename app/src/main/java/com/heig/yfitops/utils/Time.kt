package com.heig.yfitops.utils

import java.text.SimpleDateFormat
import java.util.Locale


object Time {
    fun convertToMMSS(value: Long): String {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        return dateFormat.format(value)
    }
}
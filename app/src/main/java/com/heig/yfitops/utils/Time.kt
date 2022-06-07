package com.heig.yfitops.utils

import java.text.SimpleDateFormat
import java.util.Locale


object Time {
    /**
     * Method used to convert seconds from Long into a String with the format mm:ss
     */
    fun convertToMMSS(seconds: Long): String {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        return dateFormat.format(seconds)
    }
}
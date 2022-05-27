package com.heig.yfitops.utils


object Time {
    fun convertSeconds(seconds : String): String {
        val s = seconds.toLong()
        return String.format("%02d:%02d", (s % 3600) / 60, (s % 3600) % 60)
    }
}
package com.heig.yfitops.exoplayer.callbacks

import android.app.Notification
import android.content.Intent
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.heig.yfitops.exoplayer.notification.MusicNotificationManager.Companion.NOTIFICATION_ID
import com.heig.yfitops.exoplayer.MusicService

/**
 * Manage actions on the music player notification.
 * Responsible for starting and stopping the music service.
 */
class MusicNotificationListener(private val musicService: MusicService) :
    PlayerNotificationManager.NotificationListener {

    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
        super.onNotificationCancelled(notificationId, dismissedByUser)
        musicService.apply {
            stopForeground(true)
            isForegroundService = false
            stopSelf()
        }
    }

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {
        super.onNotificationPosted(notificationId, notification, ongoing)
        musicService.apply {
            if (ongoing && !isForegroundService) {
                ContextCompat.startForegroundService(
                    this,
                    Intent(applicationContext, this::class.java)
                )
                startForeground(NOTIFICATION_ID, notification)
                isForegroundService = true
            }
        }
    }
}
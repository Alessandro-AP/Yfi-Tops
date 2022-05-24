package com.heig.yfitops.exoplayer

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.heig.yfitops.R

class MusicNotificationManager(
    private val context: Context,
    sessionToken: MediaSessionCompat.Token,
    notificationListener: PlayerNotificationManager.NotificationListener,
    private val newSongCallback: () -> Unit
) {

    private val notificationManager: PlayerNotificationManager

    init {
        val mediaController = MediaControllerCompat(context, sessionToken) // to control current media in notification
        notificationManager =
            PlayerNotificationManager.Builder(context, NOTIFICATION_ID, NOTIFICATION_CHANNEL_ID)
                .setChannelNameResourceId(R.string.notification_channel_name)
                .setChannelDescriptionResourceId(R.string.notification_channel_description)
                .setMediaDescriptionAdapter(
                    DescriptionAdapter(mediaController)
                )
                .setNotificationListener(notificationListener)
                .build().apply {
                    setSmallIcon(R.drawable.ic_music)
                    setMediaSessionToken(sessionToken)
                }
    }

    fun showNotification(player: Player) {
        notificationManager.setPlayer(player)
    }

    private inner class DescriptionAdapter(
        private val mediaController: MediaControllerCompat
    ) : PlayerNotificationManager.MediaDescriptionAdapter {

        override fun getCurrentContentTitle(player: Player): CharSequence {
            return mediaController.metadata.description.title.toString()
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return mediaController.sessionActivity
        }

        override fun getCurrentContentText(player: Player): CharSequence? {
            return mediaController.metadata.description.subtitle.toString()
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            // TODO adapt
//            Glide.with(context).asBitmap()
//                .load(mediaController.metadata.description.iconUri)
//                .into(object : CustomTarget<Bitmap>() {
//                    override fun onResourceReady(
//                        resource: Bitmap,
//                        transition: Transition<in Bitmap>?
//                    ) {
//                        callback.onBitmap(resource)
//                    }
//
//                    override fun onLoadCleared(placeholder: Drawable?) = Unit
//                })
            return null

            // autre exemple trouvé
//            val window = player.currentMediaItemIndex
//            val largeIcon: Bitmap = getLargeIcon(window)
//            if (largeIcon == null && getLargeIconUri(window) != null) {
//                // load bitmap async
//                loadBitmap(getLargeIconUri(window), callback)
//                return getPlaceholderBitmap()
//            }
//            return largeIcon
        }
    }

    companion object {
        // Maybe move that in constant file in utils ?
        const val NOTIFICATION_CHANNEL_ID = "music"
        const val NOTIFICATION_ID = 1
    }
}
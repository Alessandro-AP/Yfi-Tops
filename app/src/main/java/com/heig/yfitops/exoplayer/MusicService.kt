package com.heig.yfitops.exoplayer

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.lifecycle.Observer
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.heig.yfitops.MyApp
import com.heig.yfitops.domain.models.Song
import com.heig.yfitops.utils.Constants.MEDIA_ROOT_ID
import com.heig.yfitops.exoplayer.callbacks.MusicNotificationListener
import com.heig.yfitops.exoplayer.callbacks.MusicPlaybackPreparer
import com.heig.yfitops.exoplayer.callbacks.MusicPlayerListener
import com.heig.yfitops.utils.Resource
import kotlinx.coroutines.*

class MusicService : MediaBrowserServiceCompat() {

    lateinit var dataSourceFactory: DefaultDataSource.Factory
    private val musicSource = MusicSource()
    private lateinit var musicPlayerListener: MusicPlayerListener
    private lateinit var musicNotificationManager: MusicNotificationManager
    lateinit var exoPlayer: ExoPlayer

    // coroutine to avoid blocking UIThread
    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    // current session of music playing, that holds infos to communicate with the service
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var mediaSessionConnector: MediaSessionConnector

    var isForegroundService = false
    private var isPlayerInitialized = false

    private var curPlayingSong: MediaMetadataCompat? = null

    override fun onCreate() {
        super.onCreate()
        val observer = Observer<Resource<List<Song>>> { songs -> //Live data value has changed

            songs.data?.let { musicSource.convertFormat(it) }
            exoPlayer.stop()
            exoPlayer.clearMediaItems()
            exoPlayer.setMediaSource(musicSource.asMediaSource(dataSourceFactory))

            musicNotificationManager = MusicNotificationManager(
                this,
                mediaSession.sessionToken,
                MusicNotificationListener(this)
            ) {
                curSongDuration = exoPlayer.duration
            }

            musicNotificationManager.showNotification(exoPlayer)
        }

        (application as MyApp).repository.currentSongs.observeForever(observer)

        dataSourceFactory = DefaultDataSource.Factory(applicationContext)

        exoPlayer = ExoPlayer.Builder(applicationContext).build().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(C.CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA)
                    .build(), true
            )
            setHandleAudioBecomingNoisy(true)
        }

        musicPlayerListener = MusicPlayerListener(this)
        exoPlayer.addListener(musicPlayerListener)

        // Notification click triggers our activity
        val activityIntent = packageManager?.getLaunchIntentForPackage(packageName)?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getActivity(this, 0, it, PendingIntent.FLAG_MUTABLE)
            } else {
                PendingIntent.getActivity(this, 0, it, PendingIntent.FLAG_ONE_SHOT)
            }
        }

        mediaSession = MediaSessionCompat(this, SERVICE_TAG).apply {
            setSessionActivity(activityIntent)
            isActive = true
        }

        // Service token (from MediaBrowserServiceCompat)
        sessionToken = mediaSession.sessionToken

        val musicPlaybackPreparer = MusicPlaybackPreparer(musicSource) {
            curPlayingSong = it
            preparePlayer(musicSource.playlist, it, true)
        }

        mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setPlaybackPreparer(musicPlaybackPreparer)
        mediaSessionConnector.setQueueNavigator(MusicQueueNavigator())
        mediaSessionConnector.setPlayer(exoPlayer)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        exoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()

        exoPlayer.removeListener(musicPlayerListener)
        exoPlayer.release()
    }

    // Manage different clients for initial state, but in our case we have only one.
    // So no extra step needed
    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot(MEDIA_ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        when (parentId) {
            MEDIA_ROOT_ID -> {
                result.sendResult(musicSource.asMediaItems())
                if (!isPlayerInitialized && musicSource.playlist.isNotEmpty()) {
                    preparePlayer(musicSource.playlist, musicSource.playlist[0], false)
                    isPlayerInitialized = true
                }
            }
        }
    }

    private fun preparePlayer(
        songs: List<MediaMetadataCompat>,
        itemToPlay: MediaMetadataCompat?,
        playNow: Boolean
    ) {
        val curSongIndex = if(curPlayingSong == null) 0 else songs.indexOf(itemToPlay)
        exoPlayer.setMediaSource(musicSource.asMediaSource(dataSourceFactory))
        exoPlayer.seekTo(curSongIndex, 0L)
        exoPlayer.playWhenReady = playNow
        exoPlayer.prepare()
    }

    private inner class MusicQueueNavigator : TimelineQueueNavigator(mediaSession) {
        override fun getMediaDescription(player: Player, windowIndex: Int): MediaDescriptionCompat {
            return musicSource.playlist[windowIndex].description
        }
    }

    companion object {
        private const val SERVICE_TAG = "MusicService"
        var curSongDuration = 0L
            private set // write only inside service (but read outside)
    }
}
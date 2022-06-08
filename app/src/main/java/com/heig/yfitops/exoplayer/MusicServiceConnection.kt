package com.heig.yfitops.exoplayer

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heig.yfitops.utils.Resource

/**
 * This class allows communication between
 * activity/components and the music service.
 */
class MusicServiceConnection(
    context: Context
) {
    private val _isConnected = MutableLiveData<Resource<Boolean>>()

    private val _playbackState = MutableLiveData<PlaybackStateCompat?>()
    val playbackState: LiveData<PlaybackStateCompat?> = _playbackState

    private val _curPlayingSong = MutableLiveData<MediaMetadataCompat?>()
    val curPlayingSong: LiveData<MediaMetadataCompat?> = _curPlayingSong
    
    lateinit var mediaController: MediaControllerCompat

    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(context)

    private val mediaBrowser = MediaBrowserCompat(
        context,
        ComponentName(
            context,
            MusicService::class.java
        ),
        mediaBrowserConnectionCallback,
        null
    ).apply { connect() }

    val transportControls: MediaControllerCompat.TransportControls
        get() = mediaController.transportControls

    /**
     * This class contains a series of methods related to exoplayer that
     * will automatically be called when the corresponding event occurs.
     */
    private inner class MediaBrowserConnectionCallback(
        private val context: Context
    ) : MediaBrowserCompat.ConnectionCallback() {

        override fun onConnected() {
            Log.d("MusicServiceConnection", "CONNECTED")
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallback())
            }
            _isConnected.postValue(Resource.success(true))
        }

        override fun onConnectionSuspended() {
            Log.d("MusicServiceConnection", "SUSPENDED")

            _isConnected.postValue(
                Resource.error(
                "The connection was suspended", false
            )
            )
        }

        override fun onConnectionFailed() {
            Log.d("MusicServiceConnection", "FAILED")

            _isConnected.postValue(
                Resource.error(
                "Couldn't connect to media browser", false
            )
            )
        }
    }

    /**
     * This class contains a series of methods related to exoplayer that
     * will automatically be called when the corresponding event occurs.
     */
    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            _playbackState.postValue(state)
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            _curPlayingSong.postValue(metadata)
        }

        override fun onSessionDestroyed() {
            mediaBrowserConnectionCallback.onConnectionSuspended()
        }
    }
}


















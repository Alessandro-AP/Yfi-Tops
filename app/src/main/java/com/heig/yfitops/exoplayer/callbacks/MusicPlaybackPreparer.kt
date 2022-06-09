package com.heig.yfitops.exoplayer.callbacks

import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.heig.yfitops.exoplayer.MusicSource

/**
 * Class to which playback preparation and play actions are delegated.
 */
class MusicPlaybackPreparer(
    private val musicSource: MusicSource,
    private val playerPrepared: (MediaMetadataCompat?) -> Unit
) : MediaSessionConnector.PlaybackPreparer {

    override fun getSupportedPrepareActions(): Long {
        return PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or
                PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID
    }

    override fun onPrepareFromMediaId(mediaId: String, playWhenReady: Boolean, extras: Bundle?) {
        val itemToPlay = musicSource.playlist.find { mediaId == it.description.mediaId }
        playerPrepared(itemToPlay)
    }

    // not needed in our context
    override fun onCommand(player: Player, command: String, extras: Bundle?, cb: ResultReceiver?) = false
    override fun onPrepare(playWhenReady: Boolean) = Unit
    override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle?) = Unit
    override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle?) = Unit
}
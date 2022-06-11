package com.heig.yfitops.exoplayer.converters

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.MediaMetadataCompat.*
import androidx.core.net.toUri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.heig.yfitops.domain.models.Song

/**
 * Convert media source to different media formats that are required for exoplayer.
 */
class MusicSourceConverter {

    var playlist = emptyList<MediaMetadataCompat>()

    /**
     * Convert a list of Song entity to a list of MediaMetadataCompat entity.
     */
    fun convertFormat(firebasePlaylist: List<Song>) {
        playlist = firebasePlaylist.map { song ->
            Builder()
                .putString(METADATA_KEY_MEDIA_ID, song.id)
                .putString(METADATA_KEY_TITLE, song.title)
                .putString(METADATA_KEY_DISPLAY_TITLE, song.title)
                .putString(METADATA_KEY_DISPLAY_SUBTITLE, song.artist)
                .putString(METADATA_KEY_MEDIA_URI, song.trackUrl)
                .putString(METADATA_KEY_DISPLAY_ICON_URI, song.imageUrl)
                .putString(METADATA_KEY_ALBUM_ART_URI, song.imageUrl)
                .build()
        }
    }

    /**
     * Convert a list of MediaMetadataCompat entity to a media source for exoplayer.
     */
    fun asMediaSource(dataSourceFactory: DefaultDataSource.Factory): ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()
        playlist.forEach { song ->
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(song.getString(METADATA_KEY_MEDIA_URI).toUri()))
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        return concatenatingMediaSource
    }

    /**
     * Convert a list of MediaMetadataCompat to a list of MediaItem.
     */
    fun asMediaItems() = playlist.map { song ->
        val desc = MediaDescriptionCompat.Builder()
            .setMediaId(song.description.mediaId)
            .setTitle(song.description.title)
            .setSubtitle(song.description.subtitle)
            .setMediaUri(song.getString(METADATA_KEY_MEDIA_URI).toUri())
            .setIconUri(song.description.iconUri)
            .build()
        MediaBrowserCompat.MediaItem(desc, FLAG_PLAYABLE)
    }.toMutableList()

}
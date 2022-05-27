package com.heig.yfitops.exoplayer

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

// Convert media source to a compatible format for exoplayer
class MusicSource {

    var playlist = emptyList<MediaMetadataCompat>()

    fun convertFormat(firebasePlayist: List<Song>) {
        playlist = firebasePlayist.map { song ->
            Builder()
                .putString(METADATA_KEY_MEDIA_ID, song.id)
                .putString(METADATA_KEY_TITLE, song.title)
                .putString(METADATA_KEY_DISPLAY_TITLE, song.title)
                .putString(METADATA_KEY_ARTIST, song.artist)
                .putString(METADATA_KEY_MEDIA_URI, song.trackUrl)
                .putString(METADATA_KEY_DISPLAY_ICON_URI, song.imageUrl)
                .putString(METADATA_KEY_ALBUM_ART_URI, song.imageUrl)
                .build()
        }
    }

    fun asMediaSource(dataSourceFactory: DefaultDataSource.Factory): ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()
        playlist.forEach { song ->
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(song.getString(METADATA_KEY_MEDIA_URI).toUri()))
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        return concatenatingMediaSource
    }

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

//    var playlists = emptyList<List<MediaMetadataCompat>>()
//
//    suspend fun fetchMediaData() = withContext(Dispatchers.IO) {
//        state = STATE_INITIALIZING
//        val allPlaylists = FirebaseRepository.getAllPlaylists()
//        playlists = allPlaylists.map { playlist ->
//            playlist.songs.map { song ->
//                Builder()
//                    .putString(METADATA_KEY_MEDIA_ID, song.id)
//                    .putString(METADATA_KEY_TITLE, song.title)
//                    .putString(METADATA_KEY_DISPLAY_TITLE, song.title)
//                    .putString(METADATA_KEY_ARTIST, song.artist)
//                    .putString(METADATA_KEY_MEDIA_URI, song.trackUrl)
//                    .putString(METADATA_KEY_DISPLAY_ICON_URI, song.imageUrl)
//                    .putString(METADATA_KEY_ALBUM_ART_URI, song.imageUrl)
//                    .build()
//            }
//            // TODO besoin de le faire sur les playlist ?
////            Builder()
////                .putString(METADATA_KEY_MEDIA_ID, playlist.id)
////                .putString(METADATA_KEY_TITLE, playlist.title)
////                .putString(METADATA_KEY_DISPLAY_TITLE, playlist.title)
////                .putString(METADATA_KEY_DISPLAY_ICON_URI, playlist.imageUrl)
////                .putString(METADATA_KEY_ALBUM_ART_URI, playlist.imageUrl)
////                .putString(METADATA_KEY_ALBUM, playlist.songs)
////                .build()
//        }
//        state = STATE_INITIALIZED
//    }
//
//    fun asMediaSource(dataSourceFactory: DefaultDataSourceFactory): ConcatenatingMediaSource {
//        val concatenatingMediaSource = ConcatenatingMediaSource()
//        playlists.forEach { playlist ->
//            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(playlist.getString(METADATA_KEY_MEDIA_URI).toUri())
//            concatenatingMediaSource.addMediaSource(mediaSource)
//        }
//        return concatenatingMediaSource
//    }
//
//    fun asMediaItems() = playlists.map { playlist ->
//        playlist.map { song ->
//            val desc = MediaDescriptionCompat.Builder()
//                .setMediaId(song.description.mediaId)
//                .setTitle(song.description.title)
//                .setMediaUri(song.getString(METADATA_KEY_MEDIA_URI).toUri())
//                .setIconUri(song.description.iconUri)
//                .build()
//            MediaBrowserCompat.MediaItem(desc, FLAG_PLAYABLE)
//        }
//    }.toMutableList()
//
//
//    /* ----- State management ----- */
//
//    // music loading is async, so we need listeners to triggers actions when ready
//    private val onReadyListeners = mutableListOf<(Boolean) -> Unit>()
//
//    private var state: State = STATE_CREATED
//        set(value) {
//            if(value == STATE_INITIALIZED || value == STATE_ERROR) {
//                // data loaded, now notify listeners with the current state, to process actions.
//                synchronized(onReadyListeners) {
//                    field = value
//                    onReadyListeners.forEach { listener ->
//                        listener(state == STATE_INITIALIZED) // listeners will know if ready or error.
//                    }
//                }
//            } else {
//                field = value // STATE_INITIALIZING
//            }
//        }
//
//    // Execute an action if data source is ready, otherwise delay them.
//    fun whenReady(action: (Boolean) -> Unit): Boolean {
//        return if(state == STATE_CREATED || state == STATE_INITIALIZING) { // delay actions
//            onReadyListeners += action
//            false
//        } else {
//            action(state == STATE_INITIALIZED) // execute actions if no errors
//            true
//        }
//    }
}

// State of music source (data)
enum class State {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR
}
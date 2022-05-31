package com.heig.yfitops.viewmodels

import android.app.Application
import android.support.v4.media.MediaMetadataCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.heig.yfitops.MyApp
import com.heig.yfitops.domain.models.Song
import com.heig.yfitops.exoplayer.MusicServiceConnection
import com.heig.yfitops.exoplayer.isPlayEnabled
import com.heig.yfitops.exoplayer.isPlaying
import com.heig.yfitops.utils.Resource


class MainViewModel(private val application: Application) : ViewModel() {

    private val _mediaItems = (application as MyApp).repository.currentSongs
    val mediaItems: LiveData<Resource<List<Song>>> = _mediaItems

    fun updatePlaylist(songs: Resource<List<Song>>) =
        (application as MyApp).repository.updatePlaylist(songs)

    suspend fun getSongsByPlaylistID(id: String): List<Song> =
        (application as MyApp).repository.getSongsByPlaylistID(id)


    private val musicServiceConnection: MusicServiceConnection =
        MusicServiceConnection(application.applicationContext)

    val curPlayingSong = musicServiceConnection.curPlayingSong
    val playbackState = musicServiceConnection.playbackState


    fun skipToNextSong() {
        musicServiceConnection.transportControls.skipToNext()
    }

    fun skipToPreviousSong() {
        musicServiceConnection.transportControls.skipToPrevious()
    }

    fun seekTo(pos: Float) {
        musicServiceConnection.transportControls.seekTo(pos.toLong())
    }

    fun startStop() {
        playbackState.value?.let { playbackState ->
            when {
                playbackState.isPlaying -> musicServiceConnection.transportControls.pause()
                playbackState.isPlayEnabled -> musicServiceConnection.transportControls.play()
                else -> Unit
            }
        }
    }

    fun playSong(mediaItem: Song) {
        if (mediaItem.id != curPlayingSong.value?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
            || playbackState.value?.isPlaying == false
        )
            musicServiceConnection.transportControls.playFromMediaId(mediaItem.id, null)
    }

}


















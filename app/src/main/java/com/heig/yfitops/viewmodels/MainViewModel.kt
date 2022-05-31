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


class MainViewModel(application: Application) : ViewModel() {

    private val firebaseRepo = (application as MyApp).repository

    private val _mediaItems = firebaseRepo.currentSongs
    val mediaItems: LiveData<Resource<List<Song>>> = _mediaItems

    suspend fun updatePlaylist(id: String) {
        if (id != firebaseRepo.currentPlaylist.value) {
            val songs = Resource.success(firebaseRepo.getSongsByPlaylistID(id))
            firebaseRepo.updatePlaylist(id, songs)
        }
    }

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


















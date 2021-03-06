package com.heig.yfitops.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heig.yfitops.exoplayer.MusicService
import com.heig.yfitops.exoplayer.MusicServiceConnection
import com.heig.yfitops.exoplayer.currentPlaybackPosition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This ViewModel takes care of keeping the state of the song currently playing
 */
class SongViewModel(application: Application) : ViewModel() {

    private val musicServiceConnection: MusicServiceConnection =
        MusicServiceConnection(application.applicationContext)

    private val playbackState = musicServiceConnection.playbackState

    private val _curSongDuration = MutableLiveData<Long>(0)
    val curSongDuration: LiveData<Long> = _curSongDuration

    private val _curPlayerPosition = MutableLiveData<Long>(0)
    val curPlayerPosition: LiveData<Long> = _curPlayerPosition


    init {
        updateCurrentPlaybackPosition()
    }

    /**
     * This recursive method retrieves the current position of the song
     * every 100ms via the exoplayer and sets it in our livedata.
     */
    private fun updateCurrentPlaybackPosition() {
        viewModelScope.launch {
            val currentPosition = playbackState.value?.currentPlaybackPosition
            if (currentPosition != null && currentPosition != _curPlayerPosition.value) {
                _curPlayerPosition.value = currentPosition!!
                _curSongDuration.value = MusicService.curSongDuration
            }
            delay(100L)
            updateCurrentPlaybackPosition()
        }
    }

    /**
     * The get() method is a converter used to cast the current position from Long to Float
     */
    val currentPlayerPosition: Float
        get() {
            if (MusicService.curSongDuration > 0) {
                return _curPlayerPosition.value!!.toFloat() / MusicService.curSongDuration
            }
            return 0f
        }
}


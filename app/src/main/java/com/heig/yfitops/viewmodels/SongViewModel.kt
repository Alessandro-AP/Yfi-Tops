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

    val currentPlayerPosition: Float
        get() {
            if (MusicService.curSongDuration > 0) {
                return _curPlayerPosition.value!!.toFloat() / MusicService.curSongDuration
            }
            return 0f
        }

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

}


package com.heig.yfitops.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heig.yfitops.domain.models.Playlist
import com.heig.yfitops.domain.services.FirebaseRepository
import kotlinx.coroutines.launch

class PlaylistViewModel : ViewModel() {
    private val _playlists = MutableLiveData<List<Playlist>>()
    val playlists: LiveData<List<Playlist>> = _playlists

    init{
        viewModelScope.launch {
            _playlists.value = FirebaseRepository.getAllPlaylists()
        }
    }
}
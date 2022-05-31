package com.heig.yfitops.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heig.yfitops.MyApp
import com.heig.yfitops.domain.models.Playlist
import com.heig.yfitops.domain.services.FirebaseRepository
import kotlinx.coroutines.launch

class PlaylistViewModel(val application: Application) : ViewModel() {
    private val _playlists = MutableLiveData<List<Playlist>>()
    val playlists: LiveData<List<Playlist>> = _playlists

    init{
        viewModelScope.launch {
            _playlists.value = (application as MyApp).repository.getAllPlaylists()
        }
    }
}
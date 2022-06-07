package com.heig.yfitops.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heig.yfitops.MyApp
import com.heig.yfitops.domain.models.Playlist
import kotlinx.coroutines.launch

/**
 * This ViewModel is responsible for retrieving the list of playlists from firebase
 * and serving them via a livedata.
 */
class PlaylistViewModel(private val application: Application) : ViewModel() {
    private val _playlists = MutableLiveData<List<Playlist>>()
    val playlists: LiveData<List<Playlist>> = _playlists

    init {
        viewModelScope.launch {
            _playlists.value = (application as MyApp).repository.getAllPlaylists()
        }
    }
}
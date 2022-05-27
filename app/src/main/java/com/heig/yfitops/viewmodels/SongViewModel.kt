package com.heig.yfitops.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heig.yfitops.domain.models.Song
import com.heig.yfitops.domain.services.FirebaseRepository
import kotlinx.coroutines.launch

class SongViewModel : ViewModel() {

    private val _songsList = MutableLiveData<List<Song>>()
    val songsList : LiveData<List<Song>> = _songsList

//    private val _currentPlaylistID = MutableLiveData<String>()
//    val currentPlaylistID : LiveData<String> = _currentPlaylistID

    init{
        viewModelScope.launch {
            _songsList.value = emptyList()
//            _currentPlaylistID.value = ""
        }
    }


    fun updateSongs(id : String) {
        viewModelScope.launch {
//            _currentPlaylistID.value = id
            _songsList.value = FirebaseRepository.getSongsByPlaylistID(id)
        }
    }


}
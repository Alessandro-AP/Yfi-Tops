package com.heig.yfitops.viewmodels

import android.app.Application
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.heig.yfitops.MyApp
import com.heig.yfitops.domain.models.Song
import com.heig.yfitops.domain.services.FirebaseRepository
import com.heig.yfitops.exoplayer.MusicServiceConnection
import com.heig.yfitops.exoplayer.isPlayEnabled
import com.heig.yfitops.exoplayer.isPlaying
import com.heig.yfitops.exoplayer.isPrepared
import com.heig.yfitops.utils.Resource


class MainViewModel(
    val application: Application
) : ViewModel() {

    private val _mediaItems = (application as MyApp).repository.currentSongs
    val mediaItems: LiveData<Resource<List<Song>>> = _mediaItems

    fun updatePlaylist(songs : Resource<List<Song>>) =
        (application as MyApp).repository.updatePlaylist(songs)

    suspend fun getSongsByPlaylistID(id: String): List<Song> =
        (application as MyApp).repository.getSongsByPlaylistID(id)


    private val musicServiceConnection: MusicServiceConnection = MusicServiceConnection(application.applicationContext)

//    val isConnected = musicServiceConnection.isConnected
//    val networkError = musicServiceConnection.networkError
    val curPlayingSong = musicServiceConnection.curPlayingSong
    val playbackState = musicServiceConnection.playbackState


    init {
//        _mediaItems.postValue(Resource.loading(null))
//        musicServiceConnection.subscribe("root_id", object : MediaBrowserCompat.SubscriptionCallback() {
//            override fun onChildrenLoaded(
//                parentId: String,
//                children: MutableList<MediaBrowserCompat.MediaItem>
//            ) {
//                super.onChildrenLoaded(parentId, children)
//                val items = children.map {
//                    Song(
//                        it.mediaId!!,
//                        it.description.title.toString(),
//                        it.description.subtitle.toString(),
//                        it.description.mediaUri.toString(),
//                        it.description.iconUri.toString()
//                    )
//                }
//                _mediaItems.postValue(Resource.success(items))
//            }
//        })
    }

    fun skipToNextSong() {
        musicServiceConnection.transportControls.skipToNext()
    }

    fun skipToPreviousSong() {
        musicServiceConnection.transportControls.skipToPrevious()
    }

    fun seekTo(pos: Float) {
        musicServiceConnection.transportControls.seekTo(pos.toLong())
    }

    fun startStop(){
        playbackState.value?.let { playbackState ->
            when {
                playbackState.isPlaying ->  musicServiceConnection.transportControls.pause()
                playbackState.isPlayEnabled -> musicServiceConnection.transportControls.play()
                else -> Unit
            }
        }
    }

    fun playSong(mediaItem: Song) {
            musicServiceConnection.transportControls.playFromMediaId(mediaItem.id, null)
    }

    override fun onCleared() {
        super.onCleared()
        musicServiceConnection.unsubscribe("root_id", object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}


















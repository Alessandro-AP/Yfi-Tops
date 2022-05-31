package com.heig.yfitops.viewmodels

import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.heig.yfitops.domain.models.Song
import com.heig.yfitops.exoplayer.MusicServiceConnection
import com.heig.yfitops.exoplayer.isPlayEnabled
import com.heig.yfitops.exoplayer.isPlaying
import com.heig.yfitops.exoplayer.isPrepared
import com.heig.yfitops.utils.Resource


class MainViewModel(
    context: Context
) : ViewModel() {

    private val _mediaItems = MutableLiveData<Resource<List<Song>>>()
    val mediaItems: LiveData<Resource<List<Song>>> = _mediaItems

    private val musicServiceConnection: MusicServiceConnection = MusicServiceConnection(context)

//    val isConnected = musicServiceConnection.isConnected
//    val networkError = musicServiceConnection.networkError
    val curPlayingSong = musicServiceConnection.curPlayingSong
    val playbackState = musicServiceConnection.playbackState

    init {
        _mediaItems.postValue(Resource.loading(null))
        musicServiceConnection.subscribe("root_id", object : MediaBrowserCompat.SubscriptionCallback() {
            override fun onChildrenLoaded(
                parentId: String,
                children: MutableList<MediaBrowserCompat.MediaItem>
            ) {
                super.onChildrenLoaded(parentId, children)
                val items = children.map {
                    Song(
                        it.mediaId!!,
                        it.description.title.toString(),
                        it.description.subtitle.toString(),
                        it.description.mediaUri.toString(),
                        it.description.iconUri.toString()
                    )
                }
                _mediaItems.postValue(Resource.success(items))
            }
        })
    }

    fun skipToNextSong() {
        musicServiceConnection.transportControls.skipToNext()
    }

    fun skipToPreviousSong() {
        musicServiceConnection.transportControls.skipToPrevious()
    }

    fun seekTo(pos: Long) {
        musicServiceConnection.transportControls.seekTo(pos)
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
        val isPrepared = playbackState.value?.isPrepared ?: false
        if(isPrepared)
            musicServiceConnection.transportControls.playFromMediaId(mediaItem.id, null)
    }

    override fun onCleared() {
        super.onCleared()
        musicServiceConnection.unsubscribe("root_id", object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}


















package com.heig.yfitops.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.heig.yfitops.exoplayer.MusicService
import com.heig.yfitops.exoplayer.MusicServiceConnection
import com.heig.yfitops.exoplayer.currentPlaybackPosition
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*


class SongViewModel (
    context: Context
) : ViewModel() {

    private val musicServiceConnection: MusicServiceConnection = MusicServiceConnection(context)

    private val playbackState = musicServiceConnection.playbackState

    var currentPlaybackPosition by mutableStateOf(0L)

    val currentPlayerPosition: Float
        get() {
            if (currentSongDuration > 0) {
                return currentPlaybackPosition.toFloat() / currentSongDuration
            }
            return 0f
        }

    val currentPlaybackFormattedPosition: String
        get() = formatLong(currentPlaybackPosition)

    val currentSongFormattedPosition: String
        get() = formatLong(currentSongDuration)


    val currentSongDuration: Long
        get() = MusicService.curSongDuration

    suspend fun updateCurrentPlaybackPosition() {
        val currentPosition = playbackState.value?.currentPlaybackPosition
        if (currentPosition != null && currentPosition != currentPlaybackPosition) {
            currentPlaybackPosition = currentPosition
        }
        delay(100L)
        updateCurrentPlaybackPosition()
    }

//    fun calculateColorPalette(drawable: Bitmap, onFinish: (Color) -> Unit) {
//
//        Palette.from(drawable).generate { palette ->
//            palette?.dominantSwatch?.rgb?.let { colorValue ->
//                onFinish(Color(colorValue))
//            }
//        }
//    }

    private fun formatLong(value: Long): String {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        return dateFormat.format(value)
    }
}

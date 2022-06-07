package com.heig.yfitops.ui.components.list.song

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.heig.yfitops.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SongListView(playlistID: String , mainViewModel: MainViewModel) {

    CoroutineScope(Dispatchers.IO).launch {
        mainViewModel.updatePlaylist(playlistID)
    }
    val list by mainViewModel.mediaItems.observeAsState()

    list?.data?.let { SongList(it, mainViewModel) }
}

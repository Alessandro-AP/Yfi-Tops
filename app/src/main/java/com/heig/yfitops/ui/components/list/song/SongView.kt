package com.heig.yfitops.ui.components.list.song

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heig.yfitops.MyApp
import com.heig.yfitops.utils.Resource
import com.heig.yfitops.viewmodels.MainViewModel
import com.heig.yfitops.viewmodels.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SongListView(playlistID: String) {

    val mainViewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(LocalContext.current.applicationContext as MyApp))

    CoroutineScope(Dispatchers.IO).launch {
        mainViewModel.updatePlaylist(Resource.success(mainViewModel.getSongsByPlaylistID(playlistID)))
    }

    val list by mainViewModel.mediaItems.observeAsState()

    println("SONG LIST $list")
    list?.data?.let { if (it.isNotEmpty()) SongList(it) }
}

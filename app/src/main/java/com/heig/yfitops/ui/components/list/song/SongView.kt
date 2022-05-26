package com.heig.yfitops.ui.components.list.song

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.heig.yfitops.domain.models.Song
import com.heig.yfitops.domain.services.FirebaseRepository
import com.heig.yfitops.viewmodels.PlaylistViewModel
import com.heig.yfitops.viewmodels.SongViewModel

@Composable
fun SongListView(playlistID: String, songViewModel: SongViewModel = viewModel()) {
    val list by songViewModel.songsList.observeAsState(null)
    list?.let { SongList(it) }
}
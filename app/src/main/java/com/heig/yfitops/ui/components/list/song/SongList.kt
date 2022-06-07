package com.heig.yfitops.ui.components.list.song

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.heig.yfitops.domain.models.Song
import com.heig.yfitops.viewmodels.MainViewModel

@Composable
fun SongList(songs: List<Song>, mainViewModel: MainViewModel) {
    LazyColumn {
        items(songs) { song ->
            SongListItem(song, mainViewModel)
        }
    }
}
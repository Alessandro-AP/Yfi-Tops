package com.heig.yfitops.ui.components.list.song

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.heig.yfitops.domain.models.Song

@Composable
fun SongList(songs: List<Song>) {
    LazyColumn {
        items(songs) { song ->
            SongListItem(song)
        }
    }
}
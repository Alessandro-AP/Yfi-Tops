package com.heig.yfitops.ui.components.list.song

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.heig.yfitops.domain.models.Song

@Composable
fun SongListView(navController: NavController, songs: List<Song>) {
    SongList(navController, songs)
}
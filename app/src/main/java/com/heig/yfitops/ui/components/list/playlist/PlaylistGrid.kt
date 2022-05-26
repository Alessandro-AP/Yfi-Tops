package com.heig.yfitops.ui.components.list.playlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.heig.yfitops.domain.models.Playlist

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaylistGrid(navController: NavController, playlists: List<Playlist>) {
//    val context = LocalContext.current

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(playlists) { playlist ->
            PlaylistGridItem(navController, playlist)
        }
    }
}

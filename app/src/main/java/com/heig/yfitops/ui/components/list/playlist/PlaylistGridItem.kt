package com.heig.yfitops.ui.components.list.playlist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.heig.yfitops.domain.models.Playlist
import com.heig.yfitops.ui.navigation.Screen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlaylistGridItem(navController: NavController, playlist: Playlist) {

    fun navigateToSongScreen() {
        navController.navigate(Screen.SongScreen.withArgs(playlist.id))
    }

    Card(
        modifier = Modifier
            .size(180.dp)
            .padding(8.dp),
        onClick = {
            navigateToSongScreen()
        }
    ) {
        AsyncImage(
            model = playlist.imageUrl,
            contentDescription = null
        )
    }
}
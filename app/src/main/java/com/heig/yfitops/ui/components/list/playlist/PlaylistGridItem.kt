package com.heig.yfitops.ui.components.list.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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

        AsyncImage(
            model = playlist.imageUrl,
            contentDescription = "Playlist Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .size(180.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(percent = 5))
                .clickable { navigateToSongScreen() }
        )
}
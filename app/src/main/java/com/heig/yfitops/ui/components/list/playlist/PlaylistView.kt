package com.heig.yfitops.ui.components.list.playlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.heig.yfitops.domain.models.Playlist

/**
 * Representing the View of the Playlist Grid
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaylistView(navController: NavController, playlists: List<Playlist>) {
    Column {
        Text(
            text = "Playlists",
            fontSize = 40.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
        PlaylistGrid(navController, playlists)
    }
}
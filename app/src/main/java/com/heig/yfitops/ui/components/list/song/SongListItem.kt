package com.heig.yfitops.ui.components.list.song

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.heig.yfitops.domain.models.Song
import com.heig.yfitops.viewmodels.MainViewModel

@Composable
fun SongListItem(song: Song, mainViewModel: MainViewModel) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                mainViewModel.playSong(song)
                mainViewModel.showBottomSheet()
            }) {
        AsyncImage(
            model = song.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .clip(shape = RoundedCornerShape(5.dp))
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp)
        ) {
            Text(
                text = song.title,
                style = MaterialTheme.typography.body2
            )
        }
    }
    Divider()
}
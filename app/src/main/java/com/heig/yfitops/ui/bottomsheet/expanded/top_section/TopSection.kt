package com.heig.yfitops.ui.bottomsheet.expanded.top_section

import android.support.v4.media.MediaMetadataCompat
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.heig.yfitops.MyApp
import com.heig.yfitops.R
import com.heig.yfitops.exoplayer.converters.toSong
import com.heig.yfitops.ui.bottomsheet.expanded.controls.PlayerSlider
import com.heig.yfitops.viewmodels.MainViewModel
import com.heig.yfitops.viewmodels.MainViewModelFactory

@Composable
fun TopSection() {

    val mainViewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(LocalContext.current.applicationContext as MyApp))
    val currentSongMetadata: MediaMetadataCompat? by mainViewModel.curPlayingSong.observeAsState()
    val currentSong = currentSongMetadata?.toSong()

    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = rememberAsyncImagePainter(currentSong?.imageUrl),
            contentDescription = "Song Image",
            modifier = Modifier
                .size(420.dp)
                .padding(top = 20.dp, bottom = 60.dp),
        )

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.padding(start = 32.dp))
            {
                Text(
                    modifier = Modifier
                        .padding(bottom = 3.dp),
                    text = currentSong?.title ?: "",
                    color = Color.White,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = currentSong?.artist ?: "",
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "Feature not available yet",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
            ) {
                Icon(
                    tint = Color.White,
                    painter = painterResource(id = R.drawable.ic_baseline_favorite_border_24),
                    contentDescription = "Add favorite"
                )
            }
        }
        PlayerSlider()
    }
}


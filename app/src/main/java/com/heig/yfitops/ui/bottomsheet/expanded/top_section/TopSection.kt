package com.heig.yfitops.ui.bottomsheet.expanded.top_section

import android.support.v4.media.MediaMetadataCompat
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.heig.yfitops.exoplayer.toSong
import com.heig.yfitops.utils.Time
import com.heig.yfitops.viewmodels.MainViewModel
import com.heig.yfitops.viewmodels.MainViewModelFactory
import com.heig.yfitops.viewmodels.SongViewModel
import com.heig.yfitops.viewmodels.SongViewModelFactory

@Composable
fun TopSection() {

    val mainViewModel : MainViewModel = viewModel(factory = MainViewModelFactory(LocalContext.current.applicationContext as MyApp))
    val currentSongMetadata  : MediaMetadataCompat? by mainViewModel.curPlayingSong.observeAsState()
    val currentSong = currentSongMetadata?.toSong()

    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = rememberAsyncImagePainter(currentSong?.imageUrl ),
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
                        text = currentSong?.title ?: "song_name",
                        color = Color.White,
                        style = MaterialTheme.typography.h6
                    )
                Text(
                    text = currentSong?.artist ?: "artist",
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
        PlayerSlider("200")
    }
}

@Composable
fun PlayerSlider(seconds: String) {
    val application = LocalContext.current.applicationContext as MyApp
    val mainViewModel : MainViewModel = viewModel(factory = MainViewModelFactory(application))
    val songViewModel : SongViewModel = viewModel(factory = SongViewModelFactory(application))

    val playerState by mainViewModel.playbackState.observeAsState()
    val currentPosition = playerState?.position

    println("POSITION: $currentPosition")
    var sliderIsChanging by remember { mutableStateOf(false) }

    var localSliderValue by remember { mutableStateOf(0f) }

    val sliderProgress =
        if (sliderIsChanging) localSliderValue else songViewModel.currentPlayerPosition

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
    ) {
        Slider(
            value = currentPosition?.toFloat() ?: 0F,
            onValueChange = { newPosition ->
                localSliderValue = newPosition
                sliderIsChanging = true
            },
            onValueChangeFinished = {
                mainViewModel.seekTo(songViewModel.currentSongDuration * localSliderValue)
                sliderIsChanging = false
            },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "0:00", color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = Time.convertSeconds(seconds), color = Color.White)
        }
    }
    LaunchedEffect("playbackPosition") {
        songViewModel.updateCurrentPlaybackPosition()
    }
}
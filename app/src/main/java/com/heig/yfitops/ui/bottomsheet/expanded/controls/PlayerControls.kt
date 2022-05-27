package com.heig.yfitops.ui.bottomsheet.expanded.controls


import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heig.yfitops.R
import com.heig.yfitops.exoplayer.isPlayEnabled
import com.heig.yfitops.exoplayer.isPlaying
import com.heig.yfitops.viewmodels.MainViewModel
import com.heig.yfitops.viewmodels.MainViewModelFactory

@Composable
fun PlayerControls() {
    val mainViewModel : MainViewModel = viewModel(factory = MainViewModelFactory(LocalContext.current))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        IconButton(
            onClick = { mainViewModel.skipToPreviousSong() },
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.ic_baseline_skip_previous_24),
                tint = Color.White,
                contentDescription = "Skip Previous"
            )
        }

        PlayPauseButton(onClick = { /*TODO*/ })

        IconButton(
            onClick = { mainViewModel.skipToNextSong() },
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.ic_baseline_skip_next_24),
                tint = Color.White,
                contentDescription = "Skip Next"
            )
        }
    }
}
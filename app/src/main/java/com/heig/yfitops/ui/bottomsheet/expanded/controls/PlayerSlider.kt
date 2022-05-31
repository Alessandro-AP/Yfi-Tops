package com.heig.yfitops.ui.bottomsheet.expanded.controls

import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heig.yfitops.MyApp
import com.heig.yfitops.utils.Time
import com.heig.yfitops.viewmodels.SongViewModel
import com.heig.yfitops.viewmodels.SongViewModelFactory

@Composable
fun PlayerSlider() {
    val application = LocalContext.current.applicationContext as MyApp
    val songViewModel: SongViewModel = viewModel(factory = SongViewModelFactory(application))

    val currentPosition by songViewModel.curPlayerPosition.observeAsState()
    val currentDuration by songViewModel.curSongDuration.observeAsState()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
    ) {
        Slider(
            value = songViewModel.currentPlayerPosition,
            onValueChange = { /*TODO*/ },
            onValueChangeFinished = { /*TODO*/ },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = Time.convertToMMSS(currentPosition ?: 0L), color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = Time.convertToMMSS(currentDuration ?: 0L), color = Color.White)
        }
    }
}
package com.heig.yfitops.ui.components.list.song

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heig.yfitops.viewmodels.MainViewModel
import com.heig.yfitops.viewmodels.MainViewModelFactory

@Composable
fun SongListView(playlistID: String) {

    val mainViewModel : MainViewModel = viewModel(factory = MainViewModelFactory(LocalContext.current))
    val list by mainViewModel.mediaItems.observeAsState()
    list?.data?.let { if (it.isNotEmpty()) SongList(it) }
}

//
///**
// * Center a circular indeterminate progress bar with optional vertical bias.
// */
//@Composable
//fun CircularIndeterminateProgressBar() {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 20.dp),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            CircularProgressIndicator(
//                color = MaterialTheme.colors.primary
//            )
//        }
//}
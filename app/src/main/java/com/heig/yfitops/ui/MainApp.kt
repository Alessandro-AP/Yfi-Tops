package com.heig.yfitops.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heig.yfitops.MyApp
import com.heig.yfitops.ui.bottomsheet.collapsed.SheetCollapsed
import com.heig.yfitops.ui.bottomsheet.currentFraction
import com.heig.yfitops.ui.bottomsheet.expanded.SheetExpanded
import com.heig.yfitops.ui.navigation.Navigation
import com.heig.yfitops.ui.theme.YfiTopsTheme
import com.heig.yfitops.viewmodels.MainViewModel
import com.heig.yfitops.viewmodels.MainViewModelFactory
import com.heig.yfitops.viewmodels.PlaylistViewModel
import com.heig.yfitops.viewmodels.PlaylistViewModelFactory
import kotlinx.coroutines.launch

/**
 * Main activity and entry point of the application
 */
class MainApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YfiTopsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    HomeScreen()
                }
            }
        }
    }
}

/**
 * This function contains the heart of the entire layout, namely the BottomSheetScaffold,
 * this component is like a classic Scaffold with the difference that it has a bar
 * at the bottom which can be opened upwards.
 *
 * This component is composed of 3 main parts:
 *  - Content: the content of the scaffold (the list of playlists and songs in our case)
 *  - SheetCollapsed : the bottom bar when it is closed.
 *  - SheetExpanded : the content of the bar when it is open.
 *
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(mainViewModel: MainViewModel =
                   viewModel(factory = MainViewModelFactory(LocalContext.current.applicationContext as MyApp)),
               playlistViewModel: PlaylistViewModel =
                   viewModel(factory = PlaylistViewModelFactory(LocalContext.current.applicationContext as MyApp))) {

    val list by playlistViewModel.playlists.observeAsState(null)
    val isHidden by mainViewModel.hideBottomSheet.observeAsState(true)

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )

    val sheetToggle: () -> Unit = {
        scope.launch {
            if (scaffoldState.bottomSheetState.isCollapsed) {
                scaffoldState.bottomSheetState.expand()
            } else {
                scaffoldState.bottomSheetState.collapse()
            }
        }
    }

    val radius = (30 * scaffoldState.currentFraction).dp

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = radius, topEnd = radius),
        sheetPeekHeight = if(!isHidden) {72.dp} else {0.dp},
            sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.95f)
                    .fillMaxWidth()
                    .alpha(if(!isHidden) 1f else 0f)
            ) {
                    SheetExpanded()
                    SheetCollapsed(
                        isCollapsed = scaffoldState.bottomSheetState.isCollapsed,
                        currentFraction = scaffoldState.currentFraction,
                        onSheetClick = sheetToggle
                    )
            }
        },
    ) {
        list?.let { Navigation(list = it, mainViewModel) }
    }
}
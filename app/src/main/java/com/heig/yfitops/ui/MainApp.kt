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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heig.yfitops.MyApp
import com.heig.yfitops.ui.bottomsheet.collapsed.SheetCollapsed
import com.heig.yfitops.ui.bottomsheet.currentFraction
import com.heig.yfitops.ui.bottomsheet.expanded.SheetExpanded
import com.heig.yfitops.ui.navigation.Navigation
import com.heig.yfitops.ui.theme.YfiTopsTheme
import com.heig.yfitops.viewmodels.PlaylistViewModel
import com.heig.yfitops.viewmodels.PlaylistViewModelFactory
import kotlinx.coroutines.launch


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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    val playlistViewModel: PlaylistViewModel =
        viewModel(factory = PlaylistViewModelFactory(LocalContext.current.applicationContext as MyApp))
    val list by playlistViewModel.playlists.observeAsState(null)
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

    val title = "I Kissed a Girl"
    val radius = (30 * scaffoldState.currentFraction).dp

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = radius, topEnd = radius),
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.95f)
                    .fillMaxWidth()
            ) {
                SheetExpanded()
                SheetCollapsed(
                    isCollapsed = scaffoldState.bottomSheetState.isCollapsed,
                    currentFraction = scaffoldState.currentFraction,
                    onSheetClick = sheetToggle
                )
            }
        },
        sheetPeekHeight = 72.dp
    ) {
        list?.let { Navigation(list = it) }
    }
}
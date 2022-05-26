package com.heig.yfitops.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.heig.yfitops.domain.models.Playlist
import com.heig.yfitops.domain.models.Song
import com.heig.yfitops.ui.bottomsheet.collapsed.SheetCollapsed
import com.heig.yfitops.ui.bottomsheet.expanded.SheetExpanded
import com.heig.yfitops.ui.bottomsheet.currentFraction
import com.heig.yfitops.ui.components.list.playlist.PlaylistGrid
import com.heig.yfitops.ui.navigation.Navigation
import com.heig.yfitops.ui.theme.YfiTopsTheme
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

private val list = (1..20).map { // TODO à remplacer avec la list fetch depuis firestore
    Playlist(
        "",
        "",
        "https://e-cdns-images.dzcdn.net/images/playlist/1caf250f97b7841dcf431f65969a70fc/528x528-000000-80-0-0.jpg",
        listOf(Song("","helloWorld","","",""),Song("","helloWorld","","",""))
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
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
                SheetExpanded (name = title)
                SheetCollapsed(
                    isCollapsed = scaffoldState.bottomSheetState.isCollapsed,
                    currentFraction = scaffoldState.currentFraction,
                    onSheetClick = sheetToggle,
                    name = title
                )
            }
        },
        sheetPeekHeight = 72.dp
    ){
        Navigation(list = list)
    }
}
package com.heig.yfitops.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.heig.yfitops.domain.models.Playlist
import com.heig.yfitops.ui.theme.YfiTopsTheme

class PlaylistPage : ComponentActivity() {

    private val list = (1..20).map { // TODO à remplacer avec la list fetch depuis firestore
        Playlist(
            "",
            "",
            "https://e-cdns-images.dzcdn.net/images/playlist/1caf250f97b7841dcf431f65969a70fc/528x528-000000-80-0-0.jpg",
            emptyList()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YfiTopsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        Text(
                            text = "Playlists",
                            fontSize = 40.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        )
                        PlaylistGrid(list)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaylistGrid(list: List<Playlist>) {
    val context = LocalContext.current

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(list) { playlist ->
            Card(
                modifier = Modifier
                    .size(180.dp)
                    .padding(8.dp)
                    .clickable { context.startActivity(Intent(context, SongPage::class.java)) }
            ) {
                AsyncImage(
                    model = playlist.imageUrl,
                    contentDescription = null
                )
            }
        }
    }
}

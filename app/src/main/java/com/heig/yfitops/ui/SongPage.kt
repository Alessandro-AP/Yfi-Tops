package com.heig.yfitops.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.heig.yfitops.domain.models.Song
import com.heig.yfitops.ui.theme.YfiTopsTheme

class SongPage : ComponentActivity() {

    private val list = (1..20).map { // TODO à remplacer avec la list fetch depuis firestore
        Song(
            "",
            "Song #$it",
            "",
            "https://e-cdns-images.dzcdn.net/images/cover/6209b102b2157cf720ded3ce5bfa1bff/264x264-000000-80-0-0.jpg",
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
                            text = "Songs",
                            fontSize = 40.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        )
                        SongList(list)
                    }
                }
            }
        }
    }
}

@Composable
fun SongList(list: List<Song>) {
    LazyColumn {
        items(list) { song ->
            SongListItem(title = song.title, imageUrl = song.imageUrl)
        }
    }
}


@Composable
fun SongListItem(title: String, imageUrl: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /*TODO*/ }) {
        AsyncImage(
            model = imageUrl,
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
                text = title,
                style = typography.h6
            )
        }
    }
    Divider()
}
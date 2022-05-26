package com.heig.yfitops.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.heig.yfitops.domain.models.Playlist
import com.heig.yfitops.domain.models.Song
import com.heig.yfitops.ui.components.list.playlist.PlaylistView
import com.heig.yfitops.ui.components.list.song.SongListView
import java.util.*

@Composable
fun Navigation(list: List<Playlist>) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.PlaylistScreen.route) {
        composable(route = Screen.PlaylistScreen.route) {
            PlaylistView(navController = navController, playlists = list)
        }
        composable(route = Screen.SongScreen.route + "/{songs}",
            arguments = listOf(navArgument("songs") {
                type = NavType.StringType
            })
        ) { entry ->
            entry.arguments?.getString("songs")?.let { json ->
                val songs: List<Song> =
                    Gson().fromJson(json, object : TypeToken<List<Song>>() {}.type)
                SongListView(navController = navController, songs = songs)
            }

        }
    }
}
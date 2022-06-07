package com.heig.yfitops.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.heig.yfitops.domain.models.Playlist
import com.heig.yfitops.ui.components.list.playlist.PlaylistView
import com.heig.yfitops.ui.components.list.song.SongListView
import com.heig.yfitops.ui.splashscreen.SplashScreen
import com.heig.yfitops.viewmodels.MainViewModel

@Composable
fun Navigation(list: List<Playlist>, mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.PlaylistScreen.route) {
            PlaylistView(navController = navController, playlists = list)
        }
        composable(
            route = Screen.SongScreen.route + "/{playlistID}",
            arguments = listOf(navArgument("playlistID") {
                type = NavType.StringType
            })
        ) { entry ->
            entry.arguments?.getString("playlistID")
                ?.let { SongListView(playlistID = it, mainViewModel) }
        }
    }
}

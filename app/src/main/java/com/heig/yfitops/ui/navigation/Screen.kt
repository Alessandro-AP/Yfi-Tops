package com.heig.yfitops.ui.navigation

/**
 * Navigation Routes
 */
sealed class Screen(val route : String){
    object SplashScreen : Screen("splash_screen")
    object PlaylistScreen : Screen("playlist_screen")
    object SongScreen : Screen("song_screen")

    /**
     * Create a String with the format : route/arg1/arg2..../argN
     */
    fun withArgs(vararg args : String) : String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}

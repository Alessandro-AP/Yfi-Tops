package com.heig.yfitops.utils

/**
 * Constants for playlists and songs.
 */
object Constants {

    // Playlist Firebase Metadata
    const val FIRESTORE_PLAYLIST_COLLECTION = "Playlists"
    const val PLAYLIST_TITLE = "title"
    const val PLAYLIST_IMAGE_URL = "imageUrl"
    const val PLAYLIST_SONGS = "songs"

    // Song  Firebase Metadata
    const val FIRESTORE_SONG_COLLECTION = "Musics"
    const val SONG_TITLE = "title"
    const val SONG_ARTIST = "artist"
    const val SONG_TRACK_URL = "trackUrl"
    const val SONG_IMAGE_URL = "imageUrl"
}
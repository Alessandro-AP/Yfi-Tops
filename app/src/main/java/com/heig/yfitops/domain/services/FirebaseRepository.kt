package com.heig.yfitops.domain.services

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.heig.yfitops.domain.models.Playlist
import com.heig.yfitops.domain.models.Song
import com.heig.yfitops.utils.Costants.FIRESTORE_PLAYLIST_COLLECTION
import com.heig.yfitops.utils.Costants.PLAYLIST_IMAGE_URL
import com.heig.yfitops.utils.Costants.PLAYLIST_SONGS
import com.heig.yfitops.utils.Costants.PLAYLIST_TITLE
import com.heig.yfitops.utils.Costants.SONG_ARTIST
import com.heig.yfitops.utils.Costants.SONG_IMAGE_URL
import com.heig.yfitops.utils.Costants.SONG_TITLE
import com.heig.yfitops.utils.Costants.SONG_TRACK_URL


class FirebaseRepository {
    companion object {
        private val playlistCollection =
            FirebaseFirestore.getInstance().collection(FIRESTORE_PLAYLIST_COLLECTION)

        fun getAllPlaylists(): MutableList<Playlist> {
            val listPlaylist = mutableListOf<Playlist>()
            playlistCollection.get().addOnSuccessListener { playlists ->
                for (playlist in playlists) {
                    val listSong = mutableListOf<Song>()
                    for (song in playlist.data[PLAYLIST_SONGS] as List<*>) {
                        (song as DocumentReference).get().addOnSuccessListener { s ->
                            listSong.add(
                                Song(
                                    s.id,
                                    s.data?.get(SONG_TITLE) as String,
                                    s.data?.get(SONG_ARTIST) as String,
                                    s.data?.get(SONG_TRACK_URL) as String,
                                    s.data?.get(SONG_IMAGE_URL) as String
                                )
                            )
                        }
                    }

                    val tempPlaylist = Playlist(
                        playlist.id,
                        playlist.data[PLAYLIST_TITLE] as String,
                        playlist.data[PLAYLIST_IMAGE_URL] as String,
                        listSong
                    )
                    listPlaylist.add(tempPlaylist)
                }
            }
            Log.d("Firebase Fetch", "Playlist: => $listPlaylist")
            Log.d("Firebase Fetch", "Playlist Size: => ${listPlaylist.size}")
            return listPlaylist
        }
    }
}






package com.heig.yfitops.domain.services

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.heig.yfitops.domain.models.Playlist
import com.heig.yfitops.domain.models.Playlist.Companion.toPlaylist
import com.heig.yfitops.domain.models.Song
import com.heig.yfitops.domain.models.Song.Companion.toSong
import com.heig.yfitops.utils.Costants.FIRESTORE_PLAYLIST_COLLECTION
import com.heig.yfitops.utils.Costants.PLAYLIST_SONGS
import kotlinx.coroutines.tasks.await


class FirebaseRepository {
    companion object {
        private val playlistCollection =
            FirebaseFirestore.getInstance().collection(FIRESTORE_PLAYLIST_COLLECTION)


        suspend fun getAllPlaylists(): List<Playlist> {
            return try {
                playlistCollection
                    .get()
                    .await()
                    .documents
                    .mapNotNull { it.toPlaylist() }
            } catch (e: Exception) {
                Log.e("Fetch Playlist", "Error getting playlist", e)
                emptyList()
            }
        }

        suspend fun getSongsByPlaylistID(id : String): List<Song> {
            return try {
                val songs = mutableListOf<Song>()
                val songRef = playlistCollection
                    .document(id)
                    .get()
                    .await()
                    .get(PLAYLIST_SONGS)
                for (song in songRef as List<*>){
                    (song as DocumentReference).get().addOnSuccessListener {
                            s -> s.toSong()?.let { songs.add(it) }
                    }.await()
                }
                songs

            } catch (e: Exception) {
                Log.e("Fetch Playlist", "Error getting playlist", e)
                emptyList()
            }
        }
    }

}






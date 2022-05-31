package com.heig.yfitops.domain.services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.heig.yfitops.domain.models.Playlist
import com.heig.yfitops.domain.models.Playlist.Companion.toPlaylist
import com.heig.yfitops.domain.models.Song
import com.heig.yfitops.domain.models.Song.Companion.toSong
import com.heig.yfitops.utils.Constants.FIRESTORE_PLAYLIST_COLLECTION
import com.heig.yfitops.utils.Constants.PLAYLIST_SONGS
import com.heig.yfitops.utils.Resource
import kotlinx.coroutines.tasks.await


class FirebaseRepository {
    private val playlistCollection =
        FirebaseFirestore.getInstance().collection(FIRESTORE_PLAYLIST_COLLECTION)

    private val _currentSongs = MutableLiveData<Resource<List<Song>>>()
    val currentSongs: LiveData<Resource<List<Song>>> = _currentSongs

    private val _currentPlaylist = MutableLiveData("")
    val currentPlaylist: LiveData<String> = _currentPlaylist

    fun updatePlaylist(id: String, songs: Resource<List<Song>>) {
        _currentPlaylist.postValue(id)
        _currentSongs.postValue(songs)
    }

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

    suspend fun getSongsByPlaylistID(id: String): List<Song> {
        return try {
            val songs = mutableListOf<Song>()
            val songRef = playlistCollection
                .document(id)
                .get()
                .await()
                .get(PLAYLIST_SONGS)
            for (song in songRef as List<*>) {
                (song as DocumentReference).get().addOnSuccessListener { s ->
                    s.toSong()?.let { songs.add(it) }
                }.await()
            }
            songs

        } catch (e: Exception) {
            Log.e("Fetch Playlist", "Error getting playlist", e)
            emptyList()
        }
    }
}






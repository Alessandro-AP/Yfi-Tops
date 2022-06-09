package com.heig.yfitops.domain.models

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.heig.yfitops.utils.Constants.PLAYLIST_IMAGE_URL
import com.heig.yfitops.utils.Constants.PLAYLIST_TITLE
import kotlinx.parcelize.Parcelize

/**
 * Playlist Entity.
 */
@Parcelize
data class Playlist(
    val id: String,
    val title: String,
    val imageUrl: String
) : Parcelable {

    companion object {
        // create an Entity from Firebase data
        fun DocumentSnapshot.toPlaylist(): Playlist? {
            return try {
                Playlist(
                    id,
                    getString(PLAYLIST_TITLE)!!,
                    getString(PLAYLIST_IMAGE_URL)!!
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error converting song", e)
                null
            }
        }

        private const val TAG = "Song"
    }
}

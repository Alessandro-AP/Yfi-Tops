package com.heig.yfitops.domain.models

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.heig.yfitops.utils.Constants.SONG_ARTIST
import com.heig.yfitops.utils.Constants.SONG_IMAGE_URL
import com.heig.yfitops.utils.Constants.SONG_TITLE
import com.heig.yfitops.utils.Constants.SONG_TRACK_URL
import kotlinx.parcelize.Parcelize

/**
 * Song Entity.
 */
@Parcelize
data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val trackUrl: String,
    val imageUrl: String,
) : Parcelable {

    companion object {
        // create an Entity from Firebase data
        fun DocumentSnapshot.toSong(): Song? {
            return try {
                Song(
                    id,
                    getString(SONG_TITLE)!!,
                    getString(SONG_ARTIST)!!,
                    getString(SONG_TRACK_URL)!!,
                    getString(SONG_IMAGE_URL)!!
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error converting song", e)
                null
            }
        }

        private const val TAG = "Song"
    }
}
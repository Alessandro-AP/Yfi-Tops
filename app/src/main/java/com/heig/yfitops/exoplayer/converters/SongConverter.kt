package com.heig.yfitops.exoplayer.converters

import android.support.v4.media.MediaMetadataCompat
import com.heig.yfitops.domain.models.Song

/**
 * Mapper class. Convert a MediaMetadataCompat to a Song entity.
 */
fun MediaMetadataCompat.toSong(): Song? {
    return description?.let {
        Song(
            it.mediaId ?: "",
            it.title.toString(),
            it.subtitle.toString(),
            it.mediaUri.toString(),
            it.iconUri.toString()
        )
    }
}
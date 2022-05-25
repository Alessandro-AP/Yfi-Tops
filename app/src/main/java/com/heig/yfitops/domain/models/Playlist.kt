package com.heig.yfitops.domain.models

data class Playlist(
    val id : String,
    val title : String,
    val imageUrl : String,
    val songs: List<Song>
)

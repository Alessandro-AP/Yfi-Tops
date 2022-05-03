package com.heig.yfitops.domain.models

data class Playlist(
    val id : String,
    val name : String,
    val imageUrl : String,
    val songs: List<Song>
)

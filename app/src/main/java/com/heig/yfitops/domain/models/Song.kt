package com.heig.yfitops.domain.models

data class Song(
    val id : String,
    val title : String,
    val trackUrl : String,
    val imageUrl : String,
    val idPlaylists: List<String>
)
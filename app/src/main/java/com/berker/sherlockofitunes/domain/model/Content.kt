package com.berker.sherlockofitunes.domain.model

data class Content(
    val artistId: Int,
    val artistName: String,
    val artworkUrl100: String,
    val trackCount: Int,
    val trackExplicitness: String,
    val trackId: Int,
    val collectionName: String,
    val collectionPrice: Double,
    val releaseDate: String,
)
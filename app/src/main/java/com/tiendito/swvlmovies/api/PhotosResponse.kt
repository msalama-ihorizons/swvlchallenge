package com.tiendito.swvlmovies.api

data class PhotosResponse(val photos: Photos)

data class Photos(val photo: List<Photo>)

data class Photo(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int
)
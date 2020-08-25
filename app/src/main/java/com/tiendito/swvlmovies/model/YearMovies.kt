package com.tiendito.swvlmovies.model

import com.tiendito.swvlmovies.db.Movie

data class YearMovies(
    val year: Int,
    val movies: List<Movie>
)
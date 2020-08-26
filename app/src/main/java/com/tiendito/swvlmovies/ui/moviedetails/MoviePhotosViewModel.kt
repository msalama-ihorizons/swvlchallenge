package com.tiendito.swvlmovies.ui.moviedetails

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tiendito.swvlmovies.repository.MoviesRepository
import com.tiendito.swvlmovies.ui.moviedetails.MovieDetailsActivity.Companion.EXTRA_MOVIE_TITLE

class MoviePhotosViewModel   @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle?
): ViewModel() {

    private val movieTitle = savedStateHandle?.getLiveData<String>(EXTRA_MOVIE_TITLE)

    val moviePhotosLiveData = movieTitle?.switchMap {
            movieTitle->   moviesRepository.getMoviePhotos(movieTitle)
    }


}
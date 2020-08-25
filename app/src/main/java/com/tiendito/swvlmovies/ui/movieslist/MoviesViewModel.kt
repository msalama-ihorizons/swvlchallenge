package com.tiendito.swvlmovies.ui.movieslist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.nousapp.data.model.Resource
import com.tiendito.swvlmovies.db.Movie
import com.tiendito.swvlmovies.model.YearMovies
import com.tiendito.swvlmovies.repository.MoviesRepository

class MoviesViewModel  @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val searchKeyword = MutableLiveData<String>()

    init {
        searchMovies("")
    }

    val moviesList: LiveData<Resource<List<YearMovies>>> = searchKeyword.switchMap {
            search->   moviesRepository.searchMovies(search)
    }

     fun searchMovies(title: String) {
        searchKeyword.value = title
    }


}
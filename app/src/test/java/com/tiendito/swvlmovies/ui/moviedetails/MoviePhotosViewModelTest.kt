package com.tiendito.swvlmovies.ui.moviedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.tiendito.swvlmovies.repository.MoviesRepository
import com.tiendito.swvlmovies.ui.movieslist.MoviesViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MoviePhotosViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository = Mockito.mock(MoviesRepository::class.java)
    private val savedStateHandle = Mockito.mock(SavedStateHandle::class.java)

    @Test
    fun testGetMoviePhotos() {
        savedStateHandle.set("extraMovieTitle", "")
        Mockito.verify(repository, Mockito.never())
            .getMoviePhotos("")
    }
}
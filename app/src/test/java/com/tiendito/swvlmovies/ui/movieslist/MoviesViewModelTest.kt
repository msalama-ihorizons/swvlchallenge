package com.tiendito.swvlmovies.ui.movieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tiendito.swvlmovies.repository.MoviesRepository
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class MoviesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository = Mockito.mock(MoviesRepository::class.java)
    private val savedStateHandle = Mockito.mock(SavedStateHandle::class.java)
    private var moviesViewModel = MoviesViewModel(repository, savedStateHandle)

    @Test
    fun testSearchMovies() {
        moviesViewModel.searchMovies("whiplash")
        Mockito.verify(repository, Mockito.never())
            .searchMovies("whiplash")
    }
}
package com.tiendito.swvlmovies.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tiendito.swvlmovies.api.FlickerApis
import com.tiendito.swvlmovies.api.Photo
import com.tiendito.swvlmovies.api.Photos
import com.tiendito.swvlmovies.api.PhotosResponse
import com.tiendito.swvlmovies.db.Movie
import com.tiendito.swvlmovies.db.MoviesDao
import com.tiendito.swvlmovies.db.MoviesDatabase
import com.tiendito.swvlmovies.mock
import com.tiendito.swvlmovies.model.Resource
import com.tiendito.swvlmovies.model.YearMovies
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class MoviesRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var moviesRepository: MoviesRepository
    private val service = mock(FlickerApis::class.java)
    private val dao = mock(MoviesDao::class.java)

    @Before
    fun init() {
        val db = mock(MoviesDatabase::class.java)
        Mockito.`when`(db.moviesDao()).thenReturn(dao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        moviesRepository = MoviesRepository(service, dao)
    }

    @Test
    fun testGetMoviePhotos() {
        val observer = mock<Observer<Resource<List<Photo>>>>()

        val photo = Photo(
            id = "50263649261",
            owner = "189868260@N06",
            secret = "f85945ee77",
            server = "65535",
            farm = 54)
        val photos = Photos(listOf(photo))

        val photosResponse = PhotosResponse(photos)

        val apiResponse = Response.success(photosResponse)

        runBlocking {

            Mockito.`when`(service.searchPhotos(
                method = "flickr.photos.search",
                apiKey = "99a09da7219c20cd6e6dacd82ae76d33",
                format = "json",
                nojsoncallback = "1",
                text = "whiplash",
                page = "1",
                perPage = "10"
            )).thenReturn(apiResponse)

            moviesRepository.getMoviePhotos("whiplash").observeForever(observer)

            Mockito.verify(observer).onChanged(Resource.loading(null))

            Mockito.verify(observer).onChanged(Resource.complete(null))
            Mockito.verify(observer).onChanged(Resource.success(listOf(photo)))

        }


    }
    @Test
    fun testSearchMovies() {

        val observer = mock<Observer<Resource<List<YearMovies>>>>()
        val movies = MutableLiveData<List<Movie>>()
        val yearMovies = MutableLiveData<List<YearMovies>>()

        Mockito.`when`(dao.loadAllMoviesByTitle("peace")).thenReturn(movies)

        moviesRepository.searchMovies("peace").observeForever(observer)

        Mockito.verify(observer).onChanged(Resource.loading(null))

        movies.postValue(listOf(createMovie(), createMovie()))

        yearMovies.postValue(listOf(createYearMovie()))

        Mockito.verify(observer).onChanged(Resource.complete(null))
        Mockito.verify(observer).onChanged(Resource.success(yearMovies.value))
    }

    private fun createYearMovie() : YearMovies {
        return YearMovies(
            year = 2014,
            movies = listOf(createMovie(), createMovie())
        )
    }

    private fun createMovie() : Movie {
        return Movie(
            title = "whiplash",
            year = 2014,
            rating = 5,
            cast = listOf("J. K. Simmons", "Miles Teller"),
            genres = listOf("drama")
        )
    }
}
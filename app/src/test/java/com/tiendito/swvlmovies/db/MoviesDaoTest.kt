package com.tiendito.swvlmovies.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tiendito.swvlmovies.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var moviesDatabase: MoviesDatabase

    @Before
    fun initDB() {

        moviesDatabase =  Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java)
            .setTransactionExecutor(Dispatchers.IO.asExecutor())
            .setQueryExecutor(Dispatchers.IO.asExecutor())
            .build()
    }

    @After
    fun closeDb() {
        moviesDatabase.close()
    }

    @Test
   fun insertMovieTest() {
       val movie  = createMovie()
       runBlocking {
           moviesDatabase.moviesDao().insert(movie)
           val movies =  moviesDatabase.moviesDao().loadAllMoviesByTitle("")
            assert(movies.getOrAwaitValue().isNotEmpty())
       }
   }

    @Test
    fun insertMoviesTest() {
        val movieList  = listOf(createMovie(), createMovie(), createMovie())
        runBlocking {
            moviesDatabase.moviesDao().insertMovies(movieList)
            val movies =  moviesDatabase.moviesDao().loadAllMoviesByTitle("")
            assertThat(movies.getOrAwaitValue().size, `is`(3))
        }
    }

    @Test
    fun searchMoviesTest() {
        val movieList  = listOf(createMovie(), createMovie(), createMovie())
        runBlocking {
            moviesDatabase.moviesDao().insertMovies(movieList)
            val movies =  moviesDatabase.moviesDao().loadAllMoviesByTitle("lash")
            assertThat(movies.getOrAwaitValue().size, `is`(3))
        }
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
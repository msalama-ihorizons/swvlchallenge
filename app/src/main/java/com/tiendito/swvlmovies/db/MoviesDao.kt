package com.tiendito.swvlmovies.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Query("SELECT * from movies_table")
    fun loadMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movies_table WHERE title LIKE '%' || :title || '%' order by rating  DESC")
     fun loadAllMoviesByTitle(title: String?): LiveData<List<Movie>>

    @Query("DELETE FROM movies_table")
    suspend fun deleteAll()
}
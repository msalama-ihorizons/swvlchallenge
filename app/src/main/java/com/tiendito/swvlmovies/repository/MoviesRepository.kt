package com.tiendito.swvlmovies.repository

import androidx.lifecycle.*
import com.tiendito.swvlmovies.model.Resource
import com.tiendito.swvlmovies.api.FlickerApis
import com.tiendito.swvlmovies.api.Photo
import com.tiendito.swvlmovies.db.MoviesDao
import com.tiendito.swvlmovies.model.YearMovies
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val flickerApis: FlickerApis,
    private val moviesDao: MoviesDao

) {

    fun getMoviePhotos(movieTitle: String): LiveData<Resource<List<Photo>>> {

        return liveData {
            emit(Resource.loading(null))
            val result = flickerApis.searchPhotos(
                method = "flickr.photos.search",
                apiKey = "99a09da7219c20cd6e6dacd82ae76d33",
                format = "json",
                nojsoncallback = "1",
                text = movieTitle,
                page = "1",
                perPage = "10"
            )
            if (result.isSuccessful) {

                emit(Resource.success(result.body()?.photos?.photo))
            } else {
                emit(Resource.error(result.message(), null))
            }

            emit(Resource.complete(null))
        }

    }

    fun searchMovies(title: String): LiveData<Resource<List<YearMovies>>> {

        val result = MediatorLiveData<Resource<List<YearMovies>>>()
        result.value = Resource.loading(null)

        val moviesLiveData = moviesDao.loadAllMoviesByTitle(title)

        result.addSource(moviesLiveData) { response ->
            result.value = Resource.complete(null)
            result.value = Resource.success(
                response.groupBy { movie ->
                    movie.year
                }.toList().map { pair ->
                    YearMovies(pair.first, pair.second.take(5))
                }
            )
        }

        return result
    }
}
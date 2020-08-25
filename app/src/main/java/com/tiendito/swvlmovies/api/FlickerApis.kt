package com.tiendito.swvlmovies.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickerApis {


    @GET("rest/")
    suspend fun searchPhotos(
        @Query("method") method: String?,
        @Query("api_key") apiKey: String?,
        @Query("text") text: String?,
        @Query("format") format: String?,
        @Query("nojsoncallback") nojsoncallback: String?,
        @Query("page") page: String?,
        @Query("per_page") perPage: String?
        ) : Response<PhotosResponse>
}
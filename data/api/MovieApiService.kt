package com.adinoyi.movietracker.data.api

// import com.adinoyi.movietracker.data.models.Movie
// import retrofit2.http.GET

// interface MovieApiService {
//     @GET("latest_movies")
//     suspend fun getLatestMovies(): List<Movie>
// }


import com.adinoyi.movietracker.data.models.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") searchTerm: String,
        @Query("type") type: String = "movie",
        @Query("apikey") apiKey: String = "8be2805b"
    ): SearchResponse
}
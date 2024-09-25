package com.adinoyi.movietracker.data.api

import com.adinoyi.movietracker.data.models.Movie
import retrofit2.http.GET

interface MovieApiService {
    @GET("latest_movies")
    suspend fun getLatestMovies(): List<Movie>
}
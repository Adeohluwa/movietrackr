package com.adinoyi.movietracker.data.repositories

import com.adinoyi.movietracker.data.api.MovieApiService

class MovieRepository(private val apiService: MovieApiService) {
    suspend fun getLatestMovies() = apiService.getLatestMovies();
}
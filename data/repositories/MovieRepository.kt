// package com.adinoyi.movietracker.data.repositories

// import com.adinoyi.movietracker.data.api.MovieApiService

// class MovieRepository(private val apiService: MovieApiService) {
//     suspend fun getLatestMovies() = apiService.getLatestMovies();
// }


package com.adinoyi.movietrackr.data.repositories

import com.adinoyi.movietrackr.data.api.MovieApiService
import com.adinoyi.movietrackr.data.models.Movie

class MovieRepository(private val apiService: MovieApiService) {
    suspend fun getLatestMovies(searchTerm: String = "2023"): List<Movie> {
        val response = apiService.searchMovies(searchTerm)
        return response.movies
    }
}
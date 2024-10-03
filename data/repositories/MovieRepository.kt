package com.adinoyi.movietracker.data.repositories

import android.content.Context
import com.adinoyi.movietracker.data.api.MovieApiService
import com.adinoyi.movietracker.data.models.Movie
import retrofit2.HttpException
import java.io.IOException

class MovieRepository(
    private val apiService: MovieApiService,
    private val context: Context
) {
    suspend fun getLatestMovies(searchTerm: String = "2023"): List<Movie> {
        val response = apiService.searchMovies(searchTerm)
        return response.movies
    }

    suspend fun searchMovies(query: String, category: String = ""): List<Movie> {
        val response = apiService.searchMovies(query)
        return response.movies
    }

    suspend fun getMovieDetails(imdbID: String): Result<Movie> {
        return try {
            val movie = apiService.getMovieDetails(imdbID)
            Result.success(movie)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveFavorites(favorites: Set<String>) {
        context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
            .edit()
            .putStringSet("favorite_movies", favorites)
            .apply()
    }

    suspend fun loadFavorites(): Set<String> {
        return context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
            .getStringSet("favorite_movies", emptySet()) ?: emptySet()
    }
}
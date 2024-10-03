package com.adinoyi.movietracker.data.repositories

import android.content.Context
import com.adinoyi.movietracker.data.api.MovieApiService
import com.adinoyi.movietracker.data.models.Movie

// Repository class for handling data operations related to movies
class MovieRepository(
    private val apiService: MovieApiService, // Service to make API calls
    private val context: Context // Context to access shared preferences
) {
    // Function to fetch the latest movies from the API
    suspend fun getLatestMovies(searchTerm: String = "2023"): List<Movie> {
        // Call the API service to search for movies with the given search term
        val response = apiService.searchMovies(searchTerm)
        // Return the list of movies from the response
        return response.movies
    }

    // Function to search for movies based on a query and category
    suspend fun searchMovies(query: String, category: String = ""): List<Movie> {
        // Call the API service to search for movies with the given query
        val response = apiService.searchMovies(query)
        // Return the list of movies from the response
        return response.movies
    }

    // Function to save the set of favorite movie IDs to shared preferences
    suspend fun saveFavorites(favorites: Set<String>) {
        // Get the shared preferences editor
        context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
            .edit()
            .putStringSet("favorite_movies", favorites) // Save the set of favorite movie IDs
            .apply() // Apply the changes
    }

    // Function to load the set of favorite movie IDs from shared preferences
    suspend fun loadFavorites(): Set<String> {
        // Get the shared preferences and retrieve the set of favorite movie IDs
        return context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
            .getStringSet("favorite_movies", emptySet()) ?: emptySet()
    }
}
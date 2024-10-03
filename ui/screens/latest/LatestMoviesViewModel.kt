package com.adinoyi.movietracker.ui.screens.latest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adinoyi.movietracker.data.models.Movie
import com.adinoyi.movietracker.data.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel class for managing the state and data of the LatestMoviesScreen
class LatestMoviesViewModel(private val repository: MovieRepository) : ViewModel() {
    
    // MutableStateFlow to hold the list of latest movies
    private val _latestMovies = MutableStateFlow<List<Movie>>(emptyList())
    
    // StateFlow to expose the list of latest movies to the UI
    val latestMovies: StateFlow<List<Movie>> = _latestMovies

    // MutableStateFlow to hold the set of favorite movie IDs
    private val _favorites = MutableStateFlow<Set<String>>(emptySet())
    
    // StateFlow to expose the set of favorite movie IDs to the UI
    val favorites: StateFlow<Set<String>> = _favorites

    // Initialization block to fetch the latest movies and load favorites
    init {
        fetchLatestMovies()
        loadFavorites()
    }

    // Function to search for movies based on a query and category
    fun searchMovies(query: String, category: String) {
        viewModelScope.launch {
            try {
                // Update the latestMovies StateFlow with the search results
                _latestMovies.value = repository.searchMovies(query, category)
            } catch (e: Exception) {
                // Handle error (e.g., show a toast or log the error)
            }
        }
    }

    // Function to toggle the favorite status of a movie
    fun toggleFavorite(movieId: String) {
        viewModelScope.launch {
            // Get the current set of favorites
            val currentFavorites = _favorites.value.toMutableSet()
            
            // Toggle the favorite status
            if (currentFavorites.contains(movieId)) {
                currentFavorites.remove(movieId)
            } else {
                currentFavorites.add(movieId)
            }
            
            // Update the favorites StateFlow
            _favorites.value = currentFavorites
            
            // Save the updated favorites to the repository
            repository.saveFavorites(currentFavorites)
        }
    }

    // Function to fetch the latest movies from the repository
    private fun fetchLatestMovies() {
        viewModelScope.launch {
            try {
                // Update the latestMovies StateFlow with the fetched movies
                _latestMovies.value = repository.getLatestMovies()
            } catch (e: Exception) {
                // Handle error (e.g., show a toast or log the error)
            }
        }
    }

    // Function to load the favorites from the repository
    private fun loadFavorites() {
        viewModelScope.launch {
            // Update the favorites StateFlow with the loaded favorites
            _favorites.value = repository.loadFavorites()
        }
    }
}
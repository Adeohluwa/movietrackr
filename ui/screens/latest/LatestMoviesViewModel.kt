package com.adinoyi.movietracker.ui.screens.latest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adinoyi.movietracker.data.models.Movie
import com.adinoyi.movietracker.data.models.MovieDetails
import com.adinoyi.movietracker.data.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// class LatestMoviesViewModel(private  val repository: MovieRepository) : ViewModel() {
//     private val _latestMovies = MutableStateFlow<List<Movie>>(emptyList())
//     val  latestMovies: StateFlow<List<Movie>> = _latestMovies

//     init {
//         fetchLatestMovies()
//     }

//     private  fun fetchLatestMovies() {
//         viewModelScope.launch {
//             try {
//                 _latestMovies.value = repository.getLatestMovies()
//             } catch (e: Exception) {

//             }
//         }
//     }
// }


class LatestMoviesViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _latestMovies = MutableStateFlow<List<Movie>>(emptyList())
    val latestMovies: StateFlow<List<Movie>> = _latestMovies

    private val _favorites = MutableStateFlow<Set<String>>(emptySet())
    val favorites: StateFlow<Set<String>> = _favorites

    init {
        fetchLatestMovies()
        loadFavorites()
        // fetchMovieDetails()
    }

    fun searchMovies(query: String, category: String) {
        viewModelScope.launch {
            try {
                _latestMovies.value = repository.searchMovies(query, category)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }


    private fun fetchMovieDetails(imdbID: String) {
        viewModelScope.launch {
            val details = movieRepository.getMovieDetails(imdbID)
            if (details != null) {
                _movieDetails.value = details
            } else {
                _errorMessage.value = "Failed to fetch movie details"
            }
        }
    }


    fun toggleFavorite(movieId: String) {
        viewModelScope.launch {
            val currentFavorites = _favorites.value.toMutableSet()
            if (currentFavorites.contains(movieId)) {
                currentFavorites.remove(movieId)
            } else {
                currentFavorites.add(movieId)
            }
            _favorites.value = currentFavorites
            repository.saveFavorites(currentFavorites)
        }
    }


    private  fun fetchLatestMovies() {
        viewModelScope.launch {
            try {
                _latestMovies.value = repository.getLatestMovies()
            } catch (e: Exception) {

            }
        }
    }


    private fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.loadFavorites()
        }
    }
}
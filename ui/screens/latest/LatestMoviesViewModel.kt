package com.adinoyi.movietracker.ui.screens.latest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adinoyi.movietracker.data.models.Movie
import com.adinoyi.movietracker.data.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LatestMoviesViewModel(private  val repository: MovieRepository) : ViewModel() {
    private val _latestMovies = MutableStateFlow<List<Movie>>(emptyList())
    val  latestMovies: StateFlow<List<Movie>> = _latestMovies

    init {
        fetchLatestMovies()
    }

    private  fun fetchLatestMovies() {
        viewModelScope.launch {
            try {
                _latestMovies.value = repository.getLatestMovies()
            } catch (e: Exception) {

            }
        }
    }
}
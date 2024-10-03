import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adinoyi.movietracker.data.models.Movie
import com.adinoyi.movietracker.data.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movieState = MutableStateFlow(MovieDetailState())
    val movieState: StateFlow<MovieDetailState> = _movieState

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun loadMovieDetails(movieId: String) {
        viewModelScope.launch {
            _movieState.value = MovieDetailState(isLoading = true)
            try {
                val result = repository.getMovieDetails(movieId)
                result.fold(
                    onSuccess = { movie ->
                        _movieState.value = MovieDetailState(movie = movie)
                        checkFavoriteStatus(movieId)
                    },
                    onFailure = { error ->
                        _movieState.value = MovieDetailState(error = error.message ?: "Unknown error occurred")
                    }
                )
            } catch (e: Exception) {
                _movieState.value = MovieDetailState(error = e.message ?: "Unknown error occurred")
            }
        }
    }

    private fun checkFavoriteStatus(movieId: String) {
        viewModelScope.launch {
            val favorites = repository.loadFavorites()
            _isFavorite.value = movieId in favorites
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val currentFavorites = repository.loadFavorites().toMutableSet()
            val movieId = _movieState.value.movie?.id ?: return@launch
            if (_isFavorite.value) {
                currentFavorites.remove(movieId)
            } else {
                currentFavorites.add(movieId)
            }
            repository.saveFavorites(currentFavorites)
            _isFavorite.value = !_isFavorite.value
        }
    }
}

data class MovieDetailState(
    val movie: Movie? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
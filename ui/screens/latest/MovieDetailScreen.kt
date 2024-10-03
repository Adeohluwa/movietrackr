import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.adinoyi.movietracker.data.models.Movie
import com.adinoyi.movietracker.data.models.MovieDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movie: Movie,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onBackPressed: () -> Unit,
    viewModel: MovieDetailViewModel
) {
    val movieDetails by viewModel.movieDetails.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    // Fetch movie details when the screen is first composed
    LaunchedEffect(movie.imdbID) {
    viewModel.fetchMovieDetails(movie.imdbID)
    } 


{
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(movie.title) },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
            }
        )
        
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            movieDetails?.let { details ->
                Column(modifier = Modifier.padding(16.dp)) {
                    AsyncImage(
                        model = details.Poster,
                        contentDescription = details.Title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Year: ${details.Year}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Rated: ${details.Rated}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Released: ${details.Released}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Runtime: ${details.Runtime}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Genre: ${details.Genre}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Director: ${details.Director}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Writer: ${details.Writer}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Actors: ${details.Actors}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Plot: ${details.Plot}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
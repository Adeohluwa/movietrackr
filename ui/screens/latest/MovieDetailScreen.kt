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

// Opt-in to use experimental Material3 API features
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movie: Movie, // The movie object containing details to display
    isFavorite: Boolean, // Boolean indicating if the movie is a favorite
    onToggleFavorite: () -> Unit, // Function to toggle the favorite status
    onBackPressed: () -> Unit // Function to handle the back button press
) {
    // Column layout to arrange child components vertically
    Column(modifier = Modifier.fillMaxSize()) {
        // TopAppBar to display the movie title and navigation icons
        TopAppBar(
            title = { Text(movie.title) }, // Display the movie title
            navigationIcon = {
                // IconButton to handle the back button press
                IconButton(onClick = onBackPressed) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back") // Back arrow icon
                }
            },
            actions = {
                // IconButton to handle the favorite toggle
                IconButton(onClick = onToggleFavorite) {
                    // Display the favorite icon based on the isFavorite boolean
                    Icon(
                        if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
            }
        )
        
        // Column layout to display movie details with padding
        Column(modifier = Modifier.padding(16.dp)) {
            // AsyncImage to load and display the movie poster
            AsyncImage(
                model = movie.posterPath, // URL of the movie poster
                contentDescription = movie.title, // Description for accessibility
                modifier = Modifier
                    .fillMaxWidth() // Make the image fill the width of the screen
                    .height(300.dp), // Set the height of the image
                contentScale = ContentScale.Fit // Scale the image to fit within the bounds
            )
            // Spacer to add vertical space between components
            Spacer(modifier = Modifier.height(16.dp))
            // Text to display the movie's release year
            Text(text = "Year: ${movie.year}", style = MaterialTheme.typography.bodyLarge)
            // Spacer to add vertical space between components
            Spacer(modifier = Modifier.height(8.dp))
            // Text to display the movie type (e.g., movie, series)
            Text(text = "Type: ${movie.type}", style = MaterialTheme.typography.bodyLarge)
            // Add more details as needed
        }
    }
}
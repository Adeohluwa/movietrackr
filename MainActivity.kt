package com.adinoyi.movietracker

// Import necessary libraries and components
import MovieDetailScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adinoyi.movietracker.data.api.MovieApiService
import com.adinoyi.movietracker.data.repositories.MovieRepository
import com.adinoyi.movietracker.ui.screens.latest.LatestMoviesScreen
import com.adinoyi.movietracker.ui.screens.latest.LatestMoviesViewModel
import com.adinoyi.movietracker.ui.theme.MovieTrackerTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Main activity class that extends ComponentActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a Retrofit instance to make API calls
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/") // Base URL for the API
            .addConverterFactory(GsonConverterFactory.create()) // Convert JSON responses to Kotlin objects
            .build()

        // Create an instance of the MovieApiService using Retrofit
        val movieApiService = retrofit.create(MovieApiService::class.java)
        
        // Create an instance of the MovieRepository, which handles data operations
        val movieRepository = MovieRepository(movieApiService, applicationContext)
        
        // Create an instance of the LatestMoviesViewModel, which manages the UI-related data
        val viewModel = LatestMoviesViewModel(movieRepository)

        // Set the content of the activity using Jetpack Compose
        setContent {
            // Apply the MovieTrackerTheme to the entire UI
            MovieTrackerTheme {
                // Create a Surface that fills the entire screen and uses the background color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Create a navigation controller to handle screen navigation
                    val navController = rememberNavController()
                    
                    // Define the navigation graph for the app
                    NavHost(navController = navController, startDestination = "latestMovies") {
                        // Define the "latestMovies" screen
                        composable("latestMovies") {
                            // Display the LatestMoviesScreen and handle movie click events
                            LatestMoviesScreen(
                                viewModel = viewModel,
                                onMovieClick = { movie ->
                                    // Navigate to the movie detail screen when a movie is clicked
                                    navController.navigate("movieDetail/${movie.id}")
                                }
                            )
                        }
                        
                        // Define the "movieDetail/{movieId}" screen
                        composable("movieDetail/{movieId}") { backStackEntry ->
                            // Extract the movieId from the navigation arguments
                            val movieId = backStackEntry.arguments?.getString("movieId")
                            
                            // Find the movie with the given movieId from the viewModel
                            val movie = viewModel.latestMovies.collectAsState().value.find { it.id == movieId }
                            
                            // If the movie is found, display the MovieDetailScreen
                            if (movie != null) {
                                // Collect the state of favorites from the viewModel
                                val isFavorite by viewModel.favorites.collectAsState()
                                
                                // Display the MovieDetailScreen with the movie details and favorite status
                                MovieDetailScreen(
                                    movie = movie,
                                    isFavorite = isFavorite.contains(movie.id),
                                    onToggleFavorite = { viewModel.toggleFavorite(movie.id) },
                                    onBackPressed = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
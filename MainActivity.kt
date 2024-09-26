package com.adinoyi.movietracker

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
import com.adinoyi.moviestrackr.data.models.Movie
import com.adinoyi.moviestrackr.data.repositories.MovieRepository
import com.adinoyi.movietracker.ui.screens.latest.LatestMoviesScreen
import com.adinoyi.movietracker.ui.screens.latest.LatestMoviesViewModel
import com.adinoyi.movietracker.ui.theme.MovieTrackerTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// class MainActivity : ComponentActivity() {
//     override fun onCreate(savedInstanceState: Bundle?) {
//         super.onCreate(savedInstanceState)
//         setContent {
//             MovieTrackerTheme {
//                 // A surface container using the 'background' color from the theme
//                 Surface(
//                     modifier = Modifier.fillMaxSize(),
//                     color = MaterialTheme.colorScheme.background
//                 ) {
//                     val viewModel = LatestMoviesScreen()
//                     LatestMoviesScreen(viewModel)
//                 }
//             }
//         }
//     }
// }

// @Composable
// fun Greeting(name: String, modifier: Modifier = Modifier) {
//     Text(
//         text = "Hello $name!",
//         modifier = modifier
//     )
// }

// @Preview(showBackground = true)
// @Composable
// fun GreetingPreview() {
//     MovieTrackerTheme {
//         Greeting("Android")
//     }
// }


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val movieApiService = retrofit.create(MovieApiService::class.java)
        val movieRepository = MovieRepository(movieApiService, applicationContext)
        val viewModel = LatestMoviesViewModel(movieRepository)

        setContent {
            MoviesDailyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "latestMovies") {
                        composable("latestMovies") {
                            LatestMoviesScreen(
                                viewModel = viewModel,
                                onMovieClick = { movie ->
                                    navController.navigate("movieDetail/${movie.id}")
                                }
                            )
                        }
                        composable("movieDetail/{movieId}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")
                            val movie = viewModel.latestMovies.collectAsState().value.find { it.id == movieId }
                            if (movie != null) {
                                val isFavorite by viewModel.favorites.collectAsState()
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
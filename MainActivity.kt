package com.adinoyi.movietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.adinoyi.movietracker.ui.screens.latest.LatestMoviesScreen
import com.adinoyi.movietracker.ui.theme.MovieTrackerTheme

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
        val movieRepository = MovieRepository(movieApiService)
        val viewModel = LatestMoviesViewModel(movieRepository)

        setContent {
            MoviesDailyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LatestMoviesScreen(viewModel)
                }
            }
        }
    }
}
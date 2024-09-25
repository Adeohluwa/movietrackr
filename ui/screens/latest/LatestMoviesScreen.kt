package com.adinoyi.movietracker.ui.screens.latest

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.adinoyi.movietracker.data.models.Movie

// @Composable
// fun LatestMoviesScreen(viewModel: LatestMoviesViewModel) {
//     val movies by viewModel.latestMovies.collectAsState()
    
//     LazyVerticalGrid(
//         columns = GridCells.Fixed(2),
//         contentPadding = PaddingValues(8.dp)
//     ) {
//         items(movies) { movie ->
//             MovieItem(movie)
//         }
//     }
// }

// @Composable
// fun MovieItem(movie: Movie) {
//     Card(
//         modifier = Modifier
//             .padding(8.dp)
//             .fillMaxWidth(),
//         shape = MaterialTheme.shapes.medium
//     ) {
//         Column {
//             AsyncImage(
//                 model = movie.posterPath,
//                 contentDescription = movie.title,
//                 modifier = Modifier
//                     .fillMaxWidth()
//                     .height(200.dp),
//                 contentScale = ContentScale.Crop
//             )
//             Text(
//                 text = movie.title,
//                 modifier = Modifier.padding(8.dp),
//                 style = MaterialTheme.typography.bodyMedium
//             )
//         }
//     }
// }


@Composable
fun LatestMoviesScreen(viewModel: LatestMoviesViewModel) {
    val movies by viewModel.latestMovies.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(movies) { movie ->
            MovieItem(movie)
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            AsyncImage(
                model = movie.posterPath,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = movie.title,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = movie.year,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
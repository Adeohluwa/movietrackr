package com.adinoyi.movietracker.ui.screens.latest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
fun LatestMoviesScreen(viewModel: LatestMoviesViewModel, onMovieClick: (Movie) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Action", "Comedy", "Drama", "Sci-Fi")

    Column {
        SearchBar(
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it },
            categories = categories,
            onSearch = { viewModel.searchMovies(searchQuery, selectedCategory) }
        )
        MovieGrid(viewModel = viewModel, onMovieClick = onMovieClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    categories: List<String>,
    onSearch: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Search to find your movie...") }
        )
        Spacer(modifier = Modifier.width(8.dp))
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = {}
        ) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = {}
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = { onCategorySelected(category) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = onSearch) {
            Text("Search")
        }
    }
}

@Composable
fun MovieGrid(viewModel: LatestMoviesViewModel, onMovieClick: (Movie) -> Unit) {
    val movies by viewModel.latestMovies.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(movies) { movie ->
            MovieItem(movie = movie, onClick = { onMovieClick(movie) })
        }
    }
}


@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
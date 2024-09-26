package com.adinoyi.movietracker.ui.screens.latest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
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
    val categories = listOf("All", "Movie", "Episode", "Series")

    Scaffold(
        topBar = {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                categories = categories,
                onSearch = { viewModel.searchMovies(searchQuery, selectedCategory) }
            )
        }
    ) { paddingValues ->
        // Ensure the MovieGrid is given enough space and padding
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            MovieGrid(
                viewModel = viewModel,
                onMovieClick = onMovieClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

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
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(0.7f), // 60% width
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = {
                Text("Search to find your movie",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                ) },
            trailingIcon = {
                IconButton(onClick = onSearch) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            singleLine = true
        )
        
        Spacer(modifier = Modifier.width(8.dp))

        CategoryDropdown(
            modifier = Modifier.weight(0.3f), // 40% width
            selectedCategory = selectedCategory,
            onCategorySelected = onCategorySelected,
            categories = categories
        )

    }
}

@Composable
fun CategoryDropdown(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    categories: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    Box(modifier = modifier) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(5),

        ) {
            Text(selectedCategory)
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Select category")
        }
        
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun MovieGrid(
    viewModel: LatestMoviesViewModel,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    val movies by viewModel.latestMovies.collectAsState()

    println("Movies")
    println(movies)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier.fillMaxSize()
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
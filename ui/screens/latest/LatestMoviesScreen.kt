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

@Composable
fun LatestMoviesScreen(viewModel: LatestMoviesViewModel, onMovieClick: (Movie) -> Unit) {
    // State variables to hold the search query and selected category
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    
    // List of categories for the dropdown menu
    val categories = listOf("All", "Movie", "Episode", "Series")

    // Scaffold to provide a top bar and content area
    Scaffold(
        topBar = {
            // SearchBar composable to handle search and category selection
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
        // Box to ensure the MovieGrid is given enough space and padding
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // MovieGrid composable to display the list of movies
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
    // Row layout to arrange the search bar and category dropdown horizontally
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // OutlinedTextField for the search input
        OutlinedTextField(
            modifier = Modifier.weight(0.7f), // 60% width
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = {
                Text("Search to find your movie",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            trailingIcon = {
                // IconButton to trigger the search
                IconButton(onClick = onSearch) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            singleLine = true
        )
        
        // Spacer to add horizontal space between components
        Spacer(modifier = Modifier.width(8.dp))

        // CategoryDropdown composable to handle category selection
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
    // State variable to control the dropdown menu visibility
    var expanded by remember { mutableStateOf(false) }
    
    // Box layout to contain the dropdown button and menu
    Box(modifier = modifier) {
        // OutlinedButton to open the dropdown menu
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(5),
        ) {
            Text(selectedCategory)
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Select category")
        }
        
        // DropdownMenu to display the list of categories
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            // Iterate through the categories and create a DropdownMenuItem for each
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
    // Collect the latest movies from the ViewModel
    val movies by viewModel.latestMovies.collectAsState()

    // Print the movies to the console for debugging
    println("Movies")
    println(movies)

    // LazyVerticalGrid to display the movies in a grid layout
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        // Iterate through the movies and create a MovieItem for each
        items(movies) { movie ->
            MovieItem(movie = movie, onClick = { onMovieClick(movie) })
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    // Card to display the movie details
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium
    ) {
        // Column layout to arrange the movie poster and details vertically
        Column {
            // AsyncImage to load and display the movie poster
            AsyncImage(
                model = movie.posterPath,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            // Text to display the movie title
            Text(
                text = movie.title,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            // Text to display the movie year
            Text(
                text = movie.year,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
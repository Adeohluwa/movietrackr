package com.adinoyi.movietracker.data.api

import com.adinoyi.movietracker.data.models.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Interface defining the API service for fetching movie data
interface MovieApiService {
    // Annotation to specify the HTTP GET request method and the endpoint
    @GET("/")
    
    // Suspend function to search for movies asynchronously
    suspend fun searchMovies(
        // Query parameter "s" for the search term
        @Query("s") searchTerm: String,
        
        // Optional query parameter "type" for the type of movie (default is "movie")
        @Query("type") type: String = "movie",
        
        // Query parameter "apikey" for the API key (default is "8be2805b")
        @Query("apikey") apiKey: String = "8be2805b"
    ): SearchResponse
}
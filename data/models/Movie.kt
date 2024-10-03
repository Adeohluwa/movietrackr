package com.adinoyi.movietracker.data.models

import com.google.gson.annotations.SerializedName

// Data class representing a movie
data class Movie(
    // SerializedName annotation maps the JSON key "imdbID" to the Kotlin property "id"
    @SerializedName("imdbID") val id: String,
    
    // SerializedName annotation maps the JSON key "Title" to the Kotlin property "title"
    @SerializedName("Title") val title: String,
    
    // SerializedName annotation maps the JSON key "Poster" to the Kotlin property "posterPath"
    @SerializedName("Poster") val posterPath: String,
    
    // SerializedName annotation maps the JSON key "Year" to the Kotlin property "year"
    @SerializedName("Year") val year: String,
    
    // SerializedName annotation maps the JSON key "Type" to the Kotlin property "type"
    @SerializedName("Type") val type: String
)

// Data class representing the response from the search API
data class SearchResponse(
    // SerializedName annotation maps the JSON key "Search" to the Kotlin property "movies"
    @SerializedName("Search") val movies: List<Movie>,
    
    // SerializedName annotation maps the JSON key "totalResults" to the Kotlin property "totalResults"
    @SerializedName("totalResults") val totalResults: String,
    
    // SerializedName annotation maps the JSON key "Response" to the Kotlin property "response"
    @SerializedName("Response") val response: String
)
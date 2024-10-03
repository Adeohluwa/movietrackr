package com.adinoyi.movietracker.data.models

// data class Movie(
//    val id: Int,
//    val title: String,
//    val posterPath: String,
//    val releaseDate: String
//)

// Update the Movie data class (Movie.kt in data/models):

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("imdbID") val id: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Poster") val posterPath: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Writer") val writer: String,
    @SerializedName("Actors") val actors: String,
    @SerializedName("Plot") val plot: String
)

data class SearchResponse(
    @SerializedName("Search") val movies: List<Movie>,
    @SerializedName("totalResults") val totalResults: String,
    @SerializedName("Response") val response: String
)


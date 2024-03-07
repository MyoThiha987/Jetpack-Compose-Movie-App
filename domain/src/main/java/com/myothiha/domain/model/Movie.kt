package com.myothiha.domain.model

/**
 * @Author myothiha
 * Created 05/03/2024 at 11:24 PM.
 **/
data class Movie(
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val movieType: Int,
    val isLiked: Boolean
)
package com.myothiha.data.cache.entity

import androidx.room.Entity

/**
 * @Author myothiha
 * Created 05/03/2024 at 9:45 PM.
 **/

@Entity(tableName = "movies")
data class MovieEntity(
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
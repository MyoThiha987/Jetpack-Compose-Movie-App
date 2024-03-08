package com.myothiha.data.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author myothiha
 * Created 05/03/2024 at 9:45 PM.
 **/

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val autoId: Int? = null,
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
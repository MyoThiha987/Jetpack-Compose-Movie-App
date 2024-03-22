package com.myothiha.data.network.dto

import com.myothiha.data.cache.entity.MovieEntity
import com.myothiha.domain.model.Movie
import com.myothiha.domain.utils.extension.orFalse
import com.myothiha.domain.utils.extension.orZero
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @Author myothiha
 * Created 05/03/2024 at 9:52 PM.
 **/

@Serializable
data class MovieDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("original_title")
    val originalTitle: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("popularity")
    val popularity: Double?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?,
    val isLiked: Boolean?
)

fun MovieDto.toEntity(movieType: Int): MovieEntity {
    this.apply {
        return MovieEntity(
            id = id.orZero(),
            originalTitle = originalTitle.orEmpty(),
            overview = overview.orEmpty(),
            popularity = popularity.orZero(),
            backdropPath = backdropPath.orEmpty(),
            posterPath = posterPath.orEmpty(),
            releaseDate = releaseDate.orEmpty(),
            voteAverage = voteAverage.orZero(),
            voteCount = voteCount.orZero(),
            movieType = movieType,
            isLiked = isLiked.orFalse()
        )
    }
}

fun MovieEntity.toDomain(): Movie {
    this.apply {
        return Movie(
            id = id,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            backdropPath = backdropPath,
            posterPath = posterPath,
            releaseDate = releaseDate,
            voteAverage = voteAverage,
            voteCount = voteCount,
            movieType = movieType,
            isLiked = isLiked.orFalse()
        )
    }
}

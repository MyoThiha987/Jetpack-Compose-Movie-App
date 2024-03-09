package com.myothiha.data.network.dto


import com.myothiha.domain.model.Genre
import com.myothiha.domain.model.MovieDetail
import com.myothiha.domain.model.ProductionCompany
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("genres")
    val genres: List<GenreDto>,
    @SerialName("id")
    val id: Int,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("revenue")
    val revenue: Int,
    @SerialName("runtime")
    val runtime: Int,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)

@Serializable
data class GenreDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)

@Serializable
data class ProductionCompanyDto(
    @SerialName("id")
    val id: Int,
    @SerialName("logo_path")
    val logoPath: String? = null,
    @SerialName("name")
    val name: String
)

fun ProductionCompanyDto.toDomain(): ProductionCompany {
    this.apply {
        return ProductionCompany(id = id, logoPath = logoPath.orEmpty(), name = name)
    }
}

fun GenreDto.toDomain(): Genre {
    this.apply {
        return Genre(id = id, name = name)
    }
}

fun MovieDetailResponse.toDomain(): MovieDetail {
    this.apply {
        return MovieDetail(
            backdropPath = backdropPath,
            genres = genres.map { it.toDomain() },
            id = id,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            productionCompanies = productionCompanies.map { it.toDomain() },
            releaseDate = releaseDate,
            revenue = revenue,
            runtime = runtime,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}
package com.myothiha.domain.model

/**
 * @Author myothiha
 * Created 09/03/2024 at 4:07 PM.
 **/

data class MovieDetail(
    val backdropPath: String,
    val genres: List<Genre>,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompany>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val voteAverage: Double,
    val voteCount: Int
)

data class MovieFullDetail(
    val movieDetail: MovieDetail?,
    val credit: Credit?
)

data class Genre(
    val id: Int,
    val name: String
)

data class ProductionCompany(
    val id: Int,
    val logoPath: String,
    val name: String
)
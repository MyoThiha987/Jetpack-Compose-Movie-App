package com.myothiha.data.datasources

import com.myothiha.data.network.dto.MovieDto

/**
 * @Author myothiha
 * Created 05/03/2024 at 8:56 PM.
 **/
interface MoviesNetworkDataSource {
    suspend fun fetchMovies(): List<MovieDto>

}
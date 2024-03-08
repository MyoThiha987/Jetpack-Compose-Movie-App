package com.myothiha.data.network.utils

/**
 * @Author myothiha
 * Created 05/03/2024 at 9:52 PM.
 **/

typealias UpComing = Int
typealias NOW_PLAYING = Int
typealias TOP_RATED = Int
typealias POPULAR = Int

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/"
    const val CLIENT_TOKEN = ""
    const val GET_NOW_PLAYING = "$BASE_URL/3/movie/now_playing"
    const val GET_TOP_RATED = "$BASE_URL/3/movie/top_rated"
    const val GET_UPCOMING = "$BASE_URL/3/movie/upcoming"
    const val GET_POPULAR = "$BASE_URL/3/movie/popular"
    const val UPCOMING: UpComing = 1
    const val NOW_PLAYING: NOW_PLAYING = 2
    const val TOP_RATED: TOP_RATED = 3
    const val POPULAR: POPULAR = 4
}
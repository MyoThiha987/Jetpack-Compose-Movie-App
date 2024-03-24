package com.myothiha.cleanarchitecturestarterkit.navigation

/**
 * @Author myothiha
 * Created 13/03/2024 at 5:23 PM.
 **/

sealed class AppDestination(val route: String) {
    data object HomeScreen : AppDestination(route = "home")
    data object MovieDetailScreen : AppDestination(route = "movie_detail?id={id}") {
        fun args(movieId: Int = 0): String {
            return "movie_detail?id=$movieId"
        }

    }

    data object SeeMoreMoviesScreen :
        AppDestination(route = "seemore_movies?movie_type={movieType}") {
        fun args(movieType: Int = 0): String {
            return "seemore_movies?movie_type=$movieType"
        }
    }

    data object SearchMovieScreen : AppDestination(route = "search_movie")

    data object SaveMovieScreen : AppDestination(route = "save_movie")
    data object AccountScreen : AppDestination(route = "account")
}

object AppGraph {
    const val MAIN = "main_graph"
    const val HOME = "home_graph"
    const val SAVE = "save_graph"
    const val ACCOUNT = "account_graph"
}
package com.myothiha.cleanarchitecturestarterkit.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.accout.AccountScreen
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.home.HomeScreen
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.home.HomeViewModel
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.movie_detail.MovieDetailScreen
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.movie_detail.MovieDetailViewModel
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.save_movie.SaveMovieScreen
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.save_movie.SaveMovieViewModel
import com.myothiha.domain.utils.extension.orZero

/**
 * @Author myothiha
 * Created 13/03/2024 at 4:56 PM.
 **/

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navHostController, startDestination = AppGraph.HOME) {
        homeNavGraph(navHostController = navHostController, paddingValues)
        saveNavGraph(navHostController = navHostController,paddingValues = paddingValues)
        accountNavGraph(navHostController = navHostController)
        composable(
            route = AppDestination.MovieDetailScreen.route,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) {
            val viewmodel: MovieDetailViewModel = hiltViewModel()
            viewmodel.movieId = it.arguments?.getInt("id").orZero()
            MovieDetailScreen(
                navController = navHostController,
                viewModel = viewmodel,
                onSearchClick = { navHostController.popBackStack() },
            )
        }

    }

}

fun NavGraphBuilder.homeNavGraph(
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(startDestination = AppDestination.HomeScreen.route, route = AppGraph.HOME) {
        composable(route = AppDestination.HomeScreen.route) {
            val viewmodel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewmodel,
                navController = navHostController,
                paddingValues = paddingValues
            )
        }
    }
}

fun NavGraphBuilder.saveNavGraph(
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(startDestination = AppDestination.SaveMovieScreen.route, route = AppGraph.SAVE) {
        composable(route = AppDestination.SaveMovieScreen.route) {
            val viewmodel: SaveMovieViewModel = hiltViewModel()
            SaveMovieScreen(
                navController = navHostController,
                viewModel = viewmodel,
                paddingValues = paddingValues
            )
        }
    }
}

fun NavGraphBuilder.accountNavGraph(
    navHostController: NavHostController
) {
    navigation(startDestination = AppDestination.AccountScreen.route, route = AppGraph.ACCOUNT) {
        composable(route = AppDestination.AccountScreen.route) {
            AccountScreen(navController = navHostController)
        }
    }
}
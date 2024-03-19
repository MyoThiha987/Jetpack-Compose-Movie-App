package com.myothiha.cleanarchitecturestarterkit.ui.theme.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.myothiha.cleanarchitecturestarterkit.R
import com.myothiha.cleanarchitecturestarterkit.navigation.AppDestination
import com.myothiha.cleanarchitecturestarterkit.ui.theme.Green
import com.myothiha.cleanarchitecturestarterkit.ui.theme.Violet
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi

/**
 * @Author myothiha
 * Created 13/03/2024 at 5:53 PM.
 **/
@Composable
fun BottomNavigationBar(
    navController: NavController,
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val nav = remember {
        mutableStateOf(bottomNavList)
    }

    AnimatedVisibility(
        visible = shouldShowBottomBar(
            currentDestination = currentDestination,
            navItemList = nav
        ),
        enter = slideInVertically(
            animationSpec = tween(
                durationMillis = 500,
            ),
            initialOffsetY = { it }
        ),
        exit = slideOutVertically(
            animationSpec = tween(
                durationMillis = 500,
            ),
            targetOffsetY = { it }
        ),
    ) {
        NavigationBar(
            modifier = Modifier,
            navItemList = nav,
            navDestination = currentDestination,
            navController = navController
        )
    }

}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    navItemList: androidx.compose.runtime.State<List<BottomNavigationItem>>,
    navDestination: NavDestination?,
    navController: NavController
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(color = Violet, thickness = 1.dp)
        androidx.compose.material3.NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = modifier
        ) {
            navItemList.value.forEach { destination ->
                BottomNavigationBarItem(
                    screen = destination,
                    currentDestination = navDestination,
                    navController = navController
                )
            }

        }
    }
}

@Composable
fun RowScope.BottomNavigationBarItem(
    screen: BottomNavigationItem,
    currentDestination: NavDestination?,
    navController: NavController
) {
    NavigationBarItem(
        label = {
            Text(
                text = screen.label,
                style = MaterialTheme.typography.labelSmall
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        enabled = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } != true,
        onClick = {
            navController.navigate(screen.route) {
                navController.popBackStack()
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "Bottom Navigation Icon"
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Violet,
            unselectedIconColor = Green,
            indicatorColor = Color.Transparent,
            selectedTextColor = Violet,
            unselectedTextColor = Green,
            disabledIconColor = Violet,
            disabledTextColor = Violet
        )
    )
}

private fun shouldShowBottomBar(
    currentDestination: NavDestination?,
    navItemList: androidx.compose.runtime.State<List<BottomNavigationItem>>,
): Boolean {
    return navItemList.value.any {
        it.route == currentDestination?.route
    }
}

val bottomNavList = listOf(
    BottomNavigationItem(
        label = "Home",
        icon = R.drawable.ic_home,
        route = AppDestination.HomeScreen.route
    ),
    BottomNavigationItem(
        label = "Save",
        icon = R.drawable.ic_save,
        route = AppDestination.SaveMovieScreen.route
    ),
    BottomNavigationItem(
        label = "Account",
        icon = R.drawable.ic_user,
        route = AppDestination.AccountScreen.route
    )

)

data class BottomNavigationItem(
    val label: String,
    val icon: Int,
    val route: String
)
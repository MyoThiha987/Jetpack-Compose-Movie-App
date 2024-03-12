package com.myothiha.cleanarchitecturestarterkit.ui.theme.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.myothiha.cleanarchitecturestarterkit.R
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.movie_detail.ItemListItem

/**
 * @Author myothiha
 * Created 11/03/2024 at 9:49 PM.
 **/

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MovieTopAppBar(
    navController: NavController = rememberNavController(),
    onSearchClick: () -> Unit,
    onTopAreaBookmarkIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            IconButton(
                onClick = { navController.popBackStack() },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onSearchClick() },
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = Color.Black
                )
            }
            IconButton(
                onClick = { onTopAreaBookmarkIconClick() },
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_favourite),
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.White,
        ),
    )
}

@Composable
fun MovieDetailBodySheet(
    timetableScreenScrollState: MovieDetailScreenScrollState,
    modifier: Modifier = Modifier,
) {
    val corner by animateIntAsState(
        if (timetableScreenScrollState.isScreenLayoutCalculating || timetableScreenScrollState.isSheetExpandable) 24 else 0,
        label = "Timetable corner state",
    )
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = corner.dp, topEnd = corner.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    Text(
                        text = "Harry Potter",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(20) { index ->
                    ItemListItem(index)
                }
            }

        }
    }
}


@Preview
@Composable
fun MovieTopAppBarPreview() {
    MovieTopAppBar(
        onSearchClick = {},
        onTopAreaBookmarkIconClick = {}

    )
}

@Preview
@Composable
fun MovieDetailBodySheetPreview() {
    MovieDetailBodySheet(
        timetableScreenScrollState = rememberMovieDetailScreenScrollState(),
    )
}

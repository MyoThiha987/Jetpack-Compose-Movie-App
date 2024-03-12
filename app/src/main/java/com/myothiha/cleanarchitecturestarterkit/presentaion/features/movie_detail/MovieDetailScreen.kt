package com.myothiha.cleanarchitecturestarterkit.presentaion.features.movie_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.myothiha.cleanarchitecturestarterkit.R
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.MovieDetailBodySheet
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.MovieDetailHeader
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.MovieTopAppBar
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.rememberMovieDetailScreenScrollState
import kotlin.math.roundToInt

/**
 * @Author myothiha
 * Created 07/03/2024 at 11:42 PM.
 **/

@Composable
fun MovieDetailScreen(
    navController: NavController,
    onBookmarkIconClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberMovieDetailScreenScrollState()

    Scaffold(
        modifier = modifier
            .nestedScroll(state.screenNestedScrollConnection),
        snackbarHost = {
        },
        topBar = {
            MovieTopAppBar(
                navController = navController,
                onSearchClick,
                onBookmarkIconClick,
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentWindowInsets = WindowInsets(0.dp),
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
        ) {
            MovieDetailHeader(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        state.onHeaderPositioned(
                            coordinates.size.height.toFloat() - innerPadding.calculateTopPadding().value,
                        )
                    },
            )
            MovieDetailBodySheet(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 200.dp)
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(
                            constraints.copy(maxHeight = constraints.maxHeight - state.sheetScrollOffset.roundToInt()),
                        )
                        layout(placeable.width, placeable.height) {
                            placeable.placeRelative(
                                0,
                                0 + (state.sheetScrollOffset / 2).roundToInt(),
                            )
                        }
                    },
                timetableScreenScrollState = state,
            )
        }
    }
}

@Composable
fun ItemListItem(index: Int) {

    SideEffect {
        println("Composing $index")
    }

    Card(
        modifier = Modifier.padding(horizontal = 16.dp)) {
        Column(modifier = Modifier.background(Color.Green.copy(alpha = 0.2f))) {
            val imageModifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(topEnd = 0.dp, bottomStart = 12.dp, bottomEnd = 12.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Title $index",
                contentScale = ContentScale.Fit,
                modifier = imageModifier
            )
            Text(
                "Title $index",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}
/*@Composable
fun DetailScreen(
    navController: NavController
) {



    *//*var loadingState by remember {
        mutableStateOf(Pair(false, false))
    }

    if (loadingState.first) {
        LoadingDialog(isShowContent = loadingState.second) {
            loadingState = Pair(false, false)
            navController.popBackStack()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier.align(alignment = Alignment.Center),
            onClick = {
                loadingState = Pair(true, false)
            }
        ) {

        }

    }*//*
}

@Composable
fun LoadingDialog(
    isShowContent: Boolean,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    onClickBack: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            onClickBack()
        },
        DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        DialogContent(isShowContent = isShowContent)
    }
}

@Composable
fun DialogContent(
    isShowContent: Boolean
) {
    Box(
        modifier = Modifier
            .size(76.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        if (isShowContent) CircularProgressIndicator(
            modifier = Modifier
                .align(
                    Alignment.Center
                ),
            color = Color.Red
        )
        else Text(
            text = "Congratulations",
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}*/

@Preview
@Composable
fun TimetableScreenPreview() {
    MovieDetailScreen(
        navController = rememberNavController(),
        onBookmarkIconClick = {},
        onSearchClick = {}
    )
}



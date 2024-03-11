package com.myothiha.cleanarchitecturestarterkit.ui.theme.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myothiha.cleanarchitecturestarterkit.R
import kotlin.math.roundToInt

/**
 * @Author myothiha
 * Created 11/03/2024 at 9:49 PM.
 **/

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MovieTopAppBar(
    onSearchClick: () -> Unit,
    onTopAreaBookmarkIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = null,
            )
        },
        modifier = modifier,
        actions = {
            IconButton(
                onClick = { onSearchClick() },
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(
                onClick = { onTopAreaBookmarkIconClick() },
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_favourite),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.DarkGray,
        ),
    )
}

@Composable
fun MovieDetailBodySheet(
    timetableScreenScrollState: TimetableScreenScrollState,
    modifier: Modifier = Modifier,
) {
    val corner by animateIntAsState(
        if (timetableScreenScrollState.isScreenLayoutCalculating || timetableScreenScrollState.isSheetExpandable) 20 else 0,
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
                modifier = Modifier.fillMaxSize().padding(vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(20) { index ->
                    ItemListItem(index)
                }
            }

        }
    }
}

@Composable
fun TimetableScreen(
    onBookmarkIconClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberTimetableScreenScrollState()

    Scaffold(
        modifier = modifier
            .nestedScroll(state.screenNestedScrollConnection),
        snackbarHost = {
        },
        topBar = {
            MovieTopAppBar(
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
                    .padding(top = 190.dp)
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
        timetableScreenScrollState = rememberTimetableScreenScrollState(),
    )
}

@Preview
@Composable
fun TimetableScreenPreview() {
    TimetableScreen(
        onBookmarkIconClick = {},
        onSearchClick = {}
    )
}
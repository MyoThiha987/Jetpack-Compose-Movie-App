package com.myothiha.cleanarchitecturestarterkit.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.myothiha.cleanarchitecturestarterkit.R
import kotlinx.coroutines.launch

/**
 * @Author myothiha
 * Created 09/03/2024 at 11:38 PM.
 **/


@Composable
fun CollapsingToolbar() {

    val scrollState = rememberScrollState()
    val titleHeight = remember { mutableStateOf(0f) }
    val collapseRangePx = with(LocalDensity.current) { collapseRange.toPx() }
    val avatarSizePx = with(LocalDensity.current) { avatarSize.toPx() }
    val profileNameTopPaddingPx = with(LocalDensity.current) { paddingSmall.toPx() }
    val paddingMediumPx = with(LocalDensity.current) { paddingMedium.toPx() }

    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp

    val lazyGridState = rememberLazyGridState()


    val collapseRangeReached = remember {
        derivedStateOf {
            scrollState.value >= (collapseRangePx)
        }
    }

    val avatarZIndex = remember {
        derivedStateOf {
            if (collapseRangeReached.value)
                0f
            else
                2f
        }
    }

    val coroutineScope = rememberCoroutineScope()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                val delta = -available.y
                coroutineScope.launch {
                    if (scrollState.isScrollInProgress.not()) {
                        scrollState.scrollBy(delta)
                    }
                }
                return Offset.Zero
            }
        }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier.fillMaxSize()

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .zIndex(0f)
                    .background(Color.White)
                    .fillMaxSize()
                    .nestedScroll(nestedScrollConnection)
            ) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = lazyGridState,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.height(screenHeightDp),
                ) {

                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                // This layout is to match size to parent because
                                // inside LazyVerticalGrid we are constraint with horizontal
                                // padding
                                .layout { measurable: Measurable, constraints: Constraints ->
                                    val placeable = measurable.measure(
                                        constraints.copy(
                                            minWidth = screenWidthDp.roundToPx(),
                                            maxWidth = screenWidthDp.roundToPx()
                                        )
                                    )
                                    layout(placeable.width, placeable.height) {
                                        placeable.placeRelative(0, 0)
                                    }
                                }
                        ) {
                            Spacer(Modifier.height(headerHeight - 16.dp))
                            Text(
                                text = "Edit Profile",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                                    .border(1.dp, Gray, RoundedCornerShape(20.dp))
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }

                    item(
                        span = {
                        GridItemSpan(maxLineSpan)
                    }) {
                        Text(
                            text = "Title",
                            modifier = Modifier
                                .padding(top = paddingSmall, start = paddingMedium)
                                .onGloballyPositioned {
                                    titleHeight.value = it.size.height.toFloat()
                                }
                        )
                    }

                    item(span = {
                        GridItemSpan(maxLineSpan)
                    }) {
                        Text("Items")
                    }

                    items(20) { index ->
                        ItemListItem(index)
                    }
                }
            }

            Header(scrollState, collapseRangePx, Modifier.zIndex(1f), collapseRangeReached)
            Avatar(
                scrollState,
                collapseRangePx,
                paddingMediumPx,
                collapseRangeReached,
                avatarZIndex,
                R.drawable.ic_launcher_foreground
            )
            Toolbar(
                scrollState,
                collapseRangePx,
                titleHeight,
                avatarSizePx,
                profileNameTopPaddingPx,
                collapseRangeReached,
                Modifier
                    .zIndex(3f)
                    .statusBarsPadding()
            )
            ToolbarActions(
                Modifier
                    .zIndex(4f)
                    .statusBarsPadding()
            )
        }
    }
}

@Composable
fun ItemListItem(index: Int) {

    SideEffect {
        println("Composing $index")
    }

    Card {
        Column(modifier=Modifier.background(Color.Green.copy(alpha = 0.2f))) {
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


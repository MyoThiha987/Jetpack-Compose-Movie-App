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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.myothiha.cleanarchitecturestarterkit.R
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.Header
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.avatarSize
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.collapseRange
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.paddingMedium
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.paddingSmall
import kotlinx.coroutines.launch

/**
 * @Author myothiha
 * Created 07/03/2024 at 11:42 PM.
 **/


@Composable
fun DetailScreen(
    navController: NavController
) {



    /*var loadingState by remember {
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

    }*/
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
}



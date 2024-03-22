package com.myothiha.cleanarchitecturestarterkit.ui.theme.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myothiha.cleanarchitecturestarterkit.R
import com.myothiha.cleanarchitecturestarterkit.ui.theme.Violet

/**
 * @Author myothiha
 * Created 21/03/2024 at 5:02 PM.
 **/

@Composable
fun SettingView(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = title),
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            )
            content()
        }

    }
}

@Composable
fun SettingItemView(
    modifier: Modifier = Modifier,
    isShowSubtitle: Boolean,
    @DrawableRes headerIcon: Int = R.drawable.ic_theme,
    @StringRes title: Int = R.string.lbl_language,
    subtitle: String = "en",
    @DrawableRes tailIcon: Int = R.drawable.ic_arrow_right,


    ) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        androidx.compose.material3.Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = headerIcon),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = ""
        )

        Text(
            modifier = if (isShowSubtitle) Modifier else Modifier.weight(1f),
            text = stringResource(id = title),
            style = TextStyle(color = MaterialTheme.colorScheme.onSurface)
        )
        if (isShowSubtitle) Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = if (subtitle == "en") R.string.language_en else R.string.language_myanmar),
            style = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            textAlign = TextAlign.End
        )
        androidx.compose.material3.Icon(
            painter = painterResource(id = tailIcon),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = ""
        )
    }
}

@Composable
fun ProfileView(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //ImageView
            Image(
                modifier = Modifier
                    .size(70.dp)
                    .background(color = Violet.copy(0.4f), shape = CircleShape)
                    .padding(12.dp),
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            //Name
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = "Myo Thiha",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(color = MaterialTheme.colorScheme.onSurface)
                )
                Text(
                    text = "0997*********",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(color = MaterialTheme.colorScheme.onSurface)
                )

            }

        }
    }
}

@Composable
@Preview
fun ProfileViewPreview() {
    ProfileView()
}

@Composable
@Preview
fun SettingItemViewPreview() {
    SettingItemView(subtitle = "en", isShowSubtitle = true)
}
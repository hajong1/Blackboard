package com.hajong.blackboard.compose.common

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.hajong.blackboard.R

@Composable
fun BasicImage(
    data: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(true)
            .build(),
        loading = {},
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = onClick
            ),
        contentDescription = null
    )
}

@Composable
fun LocalImage(
    @DrawableRes id: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .animateContentSize()
    )
}
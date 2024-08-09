package com.hajong.blackboard.compose.empty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.hajong.blackboard.R
import com.hajong.blackboard.compose.common.LocalImage

@Composable
fun EmptyContainer() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LocalImage(
            id = R.drawable.ic_launcher_foreground,
            scale = ContentScale.Fit
        )
    }
}
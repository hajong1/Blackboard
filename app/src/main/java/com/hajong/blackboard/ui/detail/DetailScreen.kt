package com.hajong.blackboard.ui.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hajong.blackboard.ui.common.BasicBBTopBar
import com.hajong.blackboard.ui.empty.EmptyContainer
import com.hajong.blackboard.doodle.animatingbrush.ui.AnimatingBrushTextContainer
import com.hajong.blackboard.doodle.calendar.ui.CalendarContainer
import com.hajong.blackboard.doodle.compression.ui.ImageCompressionContainer
import com.hajong.blackboard.doodle.shimmer.ui.ShimmerContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    title: String,
    onClickBack: () -> Unit,
    path: String,
    navController: NavController,
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            BasicBBTopBar(
                title = title,
                onClickBack = onClickBack
            )
        }
    ) { paddingValues ->
        DetailScreen(
            modifier = Modifier.padding(paddingValues),
            path = path,
            navController = navController
        )
    }
}

@Composable
private fun DetailScreen(
    path: String,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DetailViewModel = viewModel(),
) {
    when(path) {
        "animatingBrushText" -> AnimatingBrushTextContainer(modifier)
        "shimmer" -> ShimmerContainer()
        "calendar" -> CalendarContainer(modifier)
        "compression" -> ImageCompressionContainer(
            modifier = modifier,
            navController= navController
        )
        else -> EmptyContainer()
    }

}
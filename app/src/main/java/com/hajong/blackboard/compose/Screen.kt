package com.hajong.blackboard.compose

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.hajong.blackboard.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
) {
    data object Home : Screen("home", R.string.Home)
    data object List : Screen("list", R.string.List)
    data object Detail : Screen("detail/{contentId}", R.string.Detail)
}
package com.hajong.blackboard.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hajong.blackboard.R
import com.hajong.blackboard.ui.detail.DetailScreen
import com.hajong.blackboard.ui.home.HomeScreen
import com.hajong.blackboard.doodle.compression.ui.UploadScreen

@Composable
fun BlackboardApp() {
    val navController = rememberNavController()
    BlackBoardNavHost(navController = navController)
}

@Composable
fun BlackBoardNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navController,
                title = stringResource(Screen.Home.resourceId),
            )
        }
        composable(route = Screen.List.route) {
            ListScreen(
                title = stringResource(Screen.List.resourceId),
                onClickBack = { navController.navigateUp() }
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("contentId") {
                    type = NavType.StringType
                    defaultValue = "empty"
                },
                navArgument("title") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            DetailScreen(
                title = stringResource(Screen.Detail.resourceId),
                onClickBack = { navController.navigateUp() },
                path = backStackEntry.arguments?.getString("contentId") ?: "",
                navController = navController,
            )
        }
        composable(route = Screen.Upload.route) {
            UploadScreen(
                title = stringResource(Screen.Upload.resourceId),
                onClickBack = { navController.navigateUp() }
            )
        }
    }
}

@Composable
fun ListScreen(
    title: String,
    onClickBack: () -> Unit = {},
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "List Screen",
            modifier = Modifier
        )
        Button(
            onClick = onClickBack,
            modifier = Modifier
        ) {
            Text(
                text = "Go Back",
                modifier = Modifier
            )
        }
    }
}

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
) {
    data object Home : Screen("home", R.string.Home)
    data object List : Screen("list", R.string.List)
    data object Detail : Screen("detail/{contentId}", R.string.Detail)
    data object Upload : Screen("upload", R.string.Upload)
}
package com.hajong.blackboard.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hajong.blackboard.compose.detail.DetailScreen
import com.hajong.blackboard.compose.home.HomeScreen
import com.hajong.blackboard.compose.list.ListScreen

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
                onClickItem = {
                    navController.navigate(
                        route = Screen.Detail.route
                    )
                }
            )
        }
        composable(route = Screen.List.route) {
            ListScreen(
                title = stringResource(Screen.List.resourceId),
                onClickBack = { navController.navigateUp() }
            )
        }
        composable(route = Screen.Detail.route) {
            DetailScreen(
                title = stringResource(Screen.Detail.resourceId),
                onClickBack = { navController.navigateUp() }
            )
        }
    }
}
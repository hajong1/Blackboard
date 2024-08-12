package com.hajong.blackboard.compose.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hajong.blackboard.R
import com.hajong.blackboard.compose.common.BasicImage
import com.hajong.blackboard.compose.common.HomeBBTopBar
import com.hajong.blackboard.compose.common.LocalImage

@Composable
fun HomeScreen(
    navController: NavController,
    title: String,
) {
    HomeScreen(
        navController = navController,
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
    )
}

@Composable
private fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {

    val list = HomeUiStatus(
        items = buildList {
            add(
                HomeUiStatus.HomeList(
                    id = "animatingBrushText",
                    title = "Animating Brush Text",
                )
            )
            add(
                HomeUiStatus.HomeList(
                    id = "shimmer",
                    title = "Shimmer",
                )
            )
            add(
                HomeUiStatus.HomeList(
                    id = "calendar",
                    title = "Calendar",
                )
            )
            repeat(22) {
                add(
                    HomeUiStatus.HomeList(
                        id = "",
                        title = "Coming Soon ${it}",
                    )
                )
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LocalImage(
            id = R.drawable.img_blackboard,
            modifier = Modifier
                .fillMaxSize()
        )
        LazyColumn(
            state = rememberLazyListState(),
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                HomeHeader(
                    modifier = Modifier
                        .padding(vertical = 40.dp)
                )
            }
            itemsIndexed(list.items) { _, item ->
                Button(
                    onClick = {
                        navController.navigate("detail/${item.id.ifBlank { "empty" }}")
                    },
                    colors = ButtonColors(
                        containerColor = Color.Unspecified,
                        contentColor = Color(0xFFF8F8F8),
                        disabledContainerColor = Color.Unspecified,
                        disabledContentColor = Color.Unspecified
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 80.dp)
                ) {
                    Text(
                        text = item.title,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.chalkboard_regular)),
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Black Board.",
            fontSize = 40.sp,
            fontFamily = FontFamily(Font(R.font.chalkboard_regular)),
            color = Color.White,
            modifier = Modifier
        )
    }
}
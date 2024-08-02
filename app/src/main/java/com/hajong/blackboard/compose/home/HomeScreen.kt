package com.hajong.blackboard.compose.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.hajong.blackboard.R
import com.hajong.blackboard.compose.common.BasicImage
import com.hajong.blackboard.compose.common.HomeBBTopBar
import com.hajong.blackboard.compose.common.LocalImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    title: String,
    onClickItem: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            HomeBBTopBar(
                title = title,
                modifier = Modifier
            )
        }
    ) { paddingValues ->
        HomeScreen(
            modifier = Modifier.padding(paddingValues),
            onClickItem = onClickItem
        )
    }
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                LocalImage(
                    id = R.drawable.img_blackboard,
                    modifier = Modifier
                )
            }
            items(20) {
                Button(
                    onClick = onClickItem,
                    modifier = Modifier
                ) {
                    Text(
                        text = "Go List Screen",
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
    
}
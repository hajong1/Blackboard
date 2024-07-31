package com.hajong.blackboard.compose.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    onClickItem: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Home Screen",
            modifier = Modifier
        )
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
        ) {
            item {

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

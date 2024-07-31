package com.hajong.blackboard.compose.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hajong.blackboard.compose.common.BasicBBTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    title: String,
    onClickBack: () -> Unit
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
        )
    }
}

@Composable
private fun DetailScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Detail Screen",
            modifier = Modifier
        )
    }
}
package com.hajong.blackboard.compose.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hajong.blackboard.compose.common.BasicBBTopBar
import com.hajong.blackboard.compose.feature.AnimatingBrushText

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
    val type = remember {
        mutableIntStateOf(1)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                type.intValue = 1
            }) {
                Text(text = "TYPE 1")
            }
            Button(onClick = {
                type.intValue = 2
            }) {
                Text(text = "TYPE 2")
            }
            Button(onClick = {
                type.intValue = 3
            }) {
                Text(text = "TYPE 3")
            }
        }
        AnimatingBrushText(
            text = "KYOBO",
            type = type.intValue,
            modifier = Modifier
        )
    }
}
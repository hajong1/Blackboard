package com.hajong.blackboard.compose.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
package com.hajong.blackboard.compose.feature.animatingbrush

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AnimatingBrushTextContainer(
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
                Text(text = "CandyCane")
            }
            Button(onClick = {
                type.intValue = 2
            }) {
                Text(text = "Rocking")
            }
            Button(onClick = {
                type.intValue = 3
            }) {
                Text(text = "Wave")
            }
        }
        AnimatingBrushText(
            text = "KYOBO",
            type = type.intValue,
            modifier = Modifier
        )
    }
}
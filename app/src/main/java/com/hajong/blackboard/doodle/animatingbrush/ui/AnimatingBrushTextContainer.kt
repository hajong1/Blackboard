package com.hajong.blackboard.doodle.animatingbrush.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatingBrushTextContainer(
    modifier: Modifier = Modifier
) {
    var type by remember { mutableIntStateOf(1) }
    var firstColor by remember { mutableLongStateOf(0xFFF74B98) }
    var secondColor by remember { mutableLongStateOf(0xFF47DAFF) }
    var isFirstColor by remember { mutableStateOf(false) }
    var isShowModal by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                type = 1
            }) {
                Text(text = "CandyCane")
            }
            Button(onClick = {
                type = 2
            }) {
                Text(text = "Rocking")
            }
            Button(onClick = {
                type = 3
            }) {
                Text(text = "Wave")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                isFirstColor = true
                isShowModal = true
            }) {
                Text(text = "Setting Color 1")
            }
            Button(onClick = {
                isFirstColor = false
                isShowModal = true
            }) {
                Text(text = "Setting Color 2")
            }
        }
        AnimatingBrushText(
            text = "아 집에 가고 싶다",
            type = type,
            colorSet = Pair(firstColor, secondColor),
            modifier = Modifier
        )

        if (isShowModal) {
            ColorPickerModal(
                hexColor = if (isFirstColor) firstColor else secondColor,
                sheetState = sheetState,
                onDismissRequest = {
                    isShowModal = it
                },
                onChangeColor = {
                    if (isFirstColor) {
                        firstColor = it
                    } else {
                        secondColor = it
                    }
                }
            )
        }
    }
}
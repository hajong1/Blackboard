package com.hajong.blackboard.compose.feature.animatingbrush

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerModal(
    hexColor: Long,
    sheetState: SheetState,
    onDismissRequest: (state: Boolean) -> Unit,
    onChangeColor: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {

    val controller = rememberColorPickerController()

    ModalBottomSheet(
        onDismissRequest = { onDismissRequest(false) },
        sheetState = sheetState,
        containerColor = Color.White,
    ) {
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(10.dp),
            controller = controller.apply {
                wheelColor = Color.DarkGray
            },
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                val hexCode = colorEnvelope.hexCode

                onChangeColor(hexCode.toLong(16))
            },
            initialColor = Color(hexColor)
        )
        
    }
}
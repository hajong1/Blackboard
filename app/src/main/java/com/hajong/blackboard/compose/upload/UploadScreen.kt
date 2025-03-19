package com.hajong.blackboard.compose.upload

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.hajong.blackboard.compose.common.BasicBBTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    title: String,
    onClickBack: () -> Unit,
    viewModel: UploadViewModel = hiltViewModel()
) {

    val itemList by viewModel.itemList.collectAsState()

    // Multiple images picker using GetMultipleContents
    val multipleImagesLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        viewModel.addItems(uris)
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            BasicBBTopBar(
                title = title,
                onClickBack = onClickBack,
                end = {
                    TextButton(
                        onClick = {
                            viewModel.startUploadItems()
                        } // 압축 시작
                    ) {
                        Text(text = "압축")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            // Display instructions
            Text(
                text = "Selected Images (${itemList.size})",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Display grid of selected images
            if (itemList.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(4.dp),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(itemList) { uri ->
                        ImageItem(uri = uri)
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "No images selected",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            // Buttons for selecting images
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        multipleImagesLauncher.launch("image/*")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(text = "Select Images")
                }

                Button(
                    onClick = viewModel::clearItemList,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Text(text = "Clear All")
                }
            }
        }
    }
}

@Composable
fun ImageItem(uri: Uri) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = uri)
                    .build()
            ),
            contentDescription = "Selected image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
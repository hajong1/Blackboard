package com.hajong.blackboard.doodle.compression.ui

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hajong.blackboard.doodle.compression.util.ParallelFileUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class UploadViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _itemList = MutableStateFlow<List<Uri>>(listOf())
    val itemList = _itemList.asStateFlow()


    fun startUploadItems() {
        val context = getApplication<Application>().applicationContext

        viewModelScope.launch {
//            val result = FileUtil.optimizeAndResizeBitmap(context, _itemList.value) // 이전 압축 로직 (직렬)
            val result = ParallelFileUtil.optimizeAndResizeBitmap(context, _itemList.value) // 현재 압축 로직 (병렬)
            result?.forEach {
                Log.d("[지용]", "이미지 처리 결과 : $it")
            }

        }
    }

    fun addItems(list: List<Uri>) {
        _itemList.update { it + list }
    }

    fun clearItemList() {
        _itemList.update { emptyList() }
    }
}
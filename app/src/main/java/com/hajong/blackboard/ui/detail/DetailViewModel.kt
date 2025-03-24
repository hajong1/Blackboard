package com.hajong.blackboard.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class DetailViewModel(
    saveStateHandle: SavedStateHandle
): ViewModel() {
    private val contentId: String = checkNotNull(saveStateHandle["contentId"])

    init {
        Log.d("[HJY]", "viewmodel savedStateHandle contentId: $contentId")
    }
}
package com.hajong.blackboard.ui.home

data class HomeUiStatus(
    val isLoading: Boolean = false,
    val items: List<HomeList> = emptyList(),
) {
    data class HomeList(
        val id: String,
        val title: String
    )
}
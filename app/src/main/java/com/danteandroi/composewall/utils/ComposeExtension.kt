package com.danteandroi.composewall.utils

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

/**
 * @author Dante
 * 2022/8/3
 */
fun LazyListState.isScrolledToBottom(preloadCount: Int = 1) =
    layoutInfo.visibleItemsInfo.lastOrNull()?.let {
        it.index > layoutInfo.totalItemsCount - preloadCount
    } ?: false

fun LazyGridState.isScrolledToBottom(preloadCount: Int = 1) =
    layoutInfo.visibleItemsInfo.lastOrNull()?.let {
        it.index > layoutInfo.totalItemsCount - preloadCount
    } ?: false

fun ScrollState.isScrolledToBottom() = this.maxValue > 0 && this.value >= (this.maxValue - 10)

fun LazyListState.isScrolledToTop() = layoutInfo.visibleItemsInfo.firstOrNull()?.index == 0

fun LazyGridState.isScrolledToTop() = layoutInfo.visibleItemsInfo.firstOrNull()?.index == 0

fun WindowSizeClass?.isExpandedScreen(): Boolean {
    if (this == null) return false
    return this.widthSizeClass >= WindowWidthSizeClass.Medium
}
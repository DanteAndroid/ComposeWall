package com.danteandroi.composewall.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

/**
 * @author Du Wenyu
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

fun WindowSizeClass?.isExpandedScreen(): Boolean {
    if (this == null) return false
    return this.widthSizeClass >= WindowWidthSizeClass.Medium
}
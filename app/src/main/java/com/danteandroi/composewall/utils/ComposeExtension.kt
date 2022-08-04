package com.danteandroi.composewall.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

/**
 * @author Du Wenyu
 * 2022/8/3
 */
fun LazyListState.isScrolledToBottom() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

fun LazyGridState.isScrolledToBottom() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

fun WindowSizeClass?.isExpandedScreen() = this?.widthSizeClass == WindowWidthSizeClass.Expanded
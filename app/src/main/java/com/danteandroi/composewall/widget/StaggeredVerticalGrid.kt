package com.danteandroi.composewall.widget

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import com.danteandroi.composewall.utils.isScrolledToBottom
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.ceil

@Composable
fun ScrollableStaggeredGrid(
    modifier: Modifier = Modifier.fillMaxSize(),
    scrollState: ScrollState,
    maxColumnWidth: Dp,
    onScrollToBottom: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        // 检测滚动到达底部
        LaunchedEffect(scrollState) {
            snapshotFlow { scrollState.value }
                .collectLatest {
                    if (scrollState.isScrolledToBottom()) {
                        onScrollToBottom.invoke()
                    }
                }
        }
        StaggeredVerticalGrid(maxColumnWidth = maxColumnWidth) {
            content()
        }
    }
}

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        check(constraints.hasBoundedWidth) { "Unbounded width not supported" }
        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}
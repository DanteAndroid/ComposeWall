package com.danteandroi.composewall.data

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

/**
 * @author Dante
 * 2022/8/5
 */
@Immutable
data class ImageDetailState(val isDetail: Boolean, val url: String)

@Stable
sealed class UiState

data class UiStateSuccess(
    val config: UiConfig = UiConfig(),
    val images: List<Image> = arrayListOf()
) : UiState()

data object LoadingUiState : UiState()
data object ErrorUiState : UiState()

data class UiConfig(
    val spanCount: Int = 2,
    val aspectRatio: Float = 0.56f,
    val type: LayoutType = LayoutType.Fixed
)

enum class LayoutType {
    Fixed, Staggered
}

package com.danteandroi.composewall.data

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

/**
 * @author Du Wenyu
 * 2022/8/5
 */

@Immutable
data class ImageDetailState(val isDetail: Boolean, val url: String)

sealed class UiState

@Stable
data class UiStateSuccess(
    val config: UiConfig = UiConfig(),
    val images: List<Image> = arrayListOf()
) : UiState()

object LoadingUiState : UiState()
object ErrorUiState : UiState()

@Stable
data class UiConfig(
    val spanCount: Int = 2,
    val aspectRatio: Float = 0.56f,
    val type: LayoutType = LayoutType.Fixed
)

enum class LayoutType {
    Fixed, Staggered
}

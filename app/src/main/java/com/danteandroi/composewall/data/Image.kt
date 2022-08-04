package com.danteandroi.composewall.data

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import java.util.Date

/**
 * @author Du Wenyu
 * 2022/7/29
 */
@Immutable
data class Image(
    val id: String,
    val type: String,
    val thumbnail: String,
    val url: String,
    val refer: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val timestamp: Date = Date()
) {
    val isValidImage get() = url.isNotEmpty() && thumbnail.isNotEmpty()

    companion object {
        val EMPTY = Image("", "", "", "")
    }

}

@Immutable
data class ImageDetailState(val isDetail: Boolean, val url: String)

@Stable
data class ImageUiState(
    val spanCount: Int = 2,
    val height: Int = 120,
    val type: LayoutType = LayoutType.Fixed,
    val images: List<Image> = arrayListOf()
)

enum class LayoutType {
    Fixed, Staggered
}

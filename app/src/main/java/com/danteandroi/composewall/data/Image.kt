package com.danteandroi.composewall.data

import androidx.compose.runtime.Immutable
import com.danteandroi.composewall.MenuItem
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
    val timestamp: Date = Date()
)

@Immutable
data class ImageUiState(val spanCount :Int = 2, val images: List<Image> = arrayListOf())

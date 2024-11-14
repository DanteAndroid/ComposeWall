package com.danteandroi.composewall.data

import androidx.compose.runtime.Immutable
import java.util.Date

/**
 * @author Dante
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

    val isValid = url.isNotEmpty() && thumbnail.isNotEmpty()

    companion object {
        val EMPTY = Image("", "", "", "")
    }

}
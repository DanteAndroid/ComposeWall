package com.danteandroi.composewall

import com.danteandroi.composewall.data.LayoutType
import com.danteandroi.composewall.data.UiConfig
import com.danteandroi.composewall.net.API
import com.danteandroi.composewall.net.Bcoderss
import com.danteandroi.composewall.net.WallHaven
import com.danteandroi.composewall.net.Yande

/**
 * @author Dante
 * 2022/7/29
 */
data class MenuItem(
    val name: String,
    val apiClazz: Class<*>,
    val baseUrl: String,
    val category: List<Pair<String, Int>>,
    val uiConfig: UiConfig
)

const val DetailThumbnailRatio = 0.4f

val SafeMenus = listOf(
    MenuItem(
        "Bcoderss",
        Bcoderss::class.java,
        API.BCODERSS_BASE,
        API.bcoderssCategories,
        UiConfig(3, 0.55f, LayoutType.Fixed)
    ),
    MenuItem(
        "WallHaven",
        WallHaven::class.java,
        API.WALL_BASE,
        API.wallHavenCategories,
        UiConfig(2, 1.5f, LayoutType.Fixed)
    )
)

val AllMenus = SafeMenus +
        MenuItem(
            "Yande",
            Yande::class.java,
            API.YANDE_BASE,
            listOf("yande" to R.string.yande),
            UiConfig(3, 0.66f, LayoutType.Staggered)
        )



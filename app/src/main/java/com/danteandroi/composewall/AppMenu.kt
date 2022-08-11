package com.danteandroi.composewall

import com.danteandroi.composewall.data.LayoutType
import com.danteandroi.composewall.data.UiConfig
import com.danteandroi.composewall.net.*

/**
 * @author Du Wenyu
 * 2022/7/29
 */


data class MenuItem(
    val name: String,
    val apiClazz: Class<*>,
    val baseUrl: String,
    val category: List<Pair<String, Int>>,
    val uiConfig: UiConfig
) {
    companion object {
        val MainMenus = listOf(
            MenuItem(
                "Bcoderss",
                Bcoderss::class.java,
                API.BCODERSS_BASE,
                API.bcoderssCategories,
                UiConfig(3, 0.49f, LayoutType.Fixed)
            ),
            MenuItem(
                "Mania",
                Mania::class.java,
                API.MANIA_BASE,
                API.maniaCategories,
                UiConfig(2, 0.56f, LayoutType.Fixed)
            ),
            MenuItem(
                "WallHaven",
                WallHaven::class.java,
                API.WALL_BASE,
                API.wallHavenCategories,
                UiConfig(2, 1.5f, LayoutType.Fixed)
            ),
            MenuItem(
                "Yande",
                Yande::class.java,
                API.YANDE_BASE,
                listOf("yande" to R.string.yande),
                UiConfig(3, 0.66f, LayoutType.Fixed)
            ),
        )
    }
}




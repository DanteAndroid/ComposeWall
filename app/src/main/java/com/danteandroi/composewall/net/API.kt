package com.danteandroi.composewall.net

import com.danteandroi.composewall.R

/**
 * Net request APIs and CATEs
 */
@Suppress("unused")
object API {

    const val GITHUB_RAW = "https://raw.githubusercontent.com/DanteAndroid/ComposeWall/master/"
    const val DOWNLOAD_BASE = "https://github.com/DanteAndroid/ComposeWall/releases/download/"

    const val WALL_BASE = "https://wallhaven.cc/"
    const val YANDE_BASE = "https://yande.re/"
    const val BCODERSS_BASE = "https://m.bcoderss.com/"

    val wallHavenCategories =
        listOf(
            "random" to R.string.random,
            "anime" to R.string.anime,
            "girl" to R.string.girl,
            "fantasy" to R.string.fantasy,
            "landscape" to R.string.landscape,
            "dark" to R.string.dark,
            "simple" to R.string.simple
        )
    val bcoderssCategories =
        listOf(
            "" to R.string.latest,
            "动漫" to R.string.anime,
            "美女" to R.string.girl,
            "4K" to R.string.four_k,
            "抽象" to R.string.fantasy,
            "游戏" to R.string.game,
            "动物" to R.string.animal,
            "风景" to R.string.landscape,
            "黑色" to R.string.dark
        )

}

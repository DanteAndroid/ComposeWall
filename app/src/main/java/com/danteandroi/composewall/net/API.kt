package com.danteandroi.composewall.net

/**
 * Net request APIs and CATEs
 */
object API {

    const val GITHUB_RAW = "https://raw.githubusercontent.com/DanteAndroid/ThreeDimens/master/"
    const val DOWNLOAD_BASE = "https://github.com/DanteAndroid/ThreeDimens/releases/download/"

    const val WALL_BASE = "https://wallhaven.cc/"
    const val YANDE_BASE = "https://yande.re/"
    const val MANIA_BASE = "https://www.moviemania.io/"
    const val BCODERSS_BASE = "https://m.bcoderss.com/"

    val wallHavenCategories =
        arrayOf("random", "anime", "girl", "fantasy", "landscape", "dark", "simple")
    val maniaCategories =
        arrayOf("popular", "trending", "new")
    val bcoderssCategories =
        arrayOf("动物", "风景", "美女", "动漫", "抽象", "游戏", "4K", "黑色")

}

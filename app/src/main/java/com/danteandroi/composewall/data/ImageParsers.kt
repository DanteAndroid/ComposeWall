package com.danteandroi.composewall.data

import com.danteandroi.composewall.data.parser.WallParser
import com.danteandroi.composewall.data.parser.YandeParser
import com.danteandroi.composewall.net.API
import com.danteandroi.composewall.net.WallHaven
import com.danteandroi.composewall.net.Yande

/**
 * @author Du Wenyu
 * 2022/7/29
 */
class ImageParser(
    val apiClazz: Class<*>,
    val baseUrl: String,
    val category: String = "",
    val parser: IParser
) {
    companion object {
        val WallParsers = listOf(
            ImageParser(
                WallHaven::class.java,
                API.WALL_BASE,
                category = API.CATE_WH_RANDOM,
                WallParser
            ),
            ImageParser(
                WallHaven::class.java,
                API.WALL_BASE,
                category = API.CATE_WH_ANIME,
                WallParser
            ),
            ImageParser(
                WallHaven::class.java,
                API.WALL_BASE,
                category = API.CATE_WH_FANTASY,
                WallParser
            ),
            ImageParser(
                WallHaven::class.java,
                API.WALL_BASE,
                category = API.CATE_WH_GIRL,
                WallParser
            ),
            ImageParser(
                WallHaven::class.java,
                API.WALL_BASE,
                category = API.CATE_WH_LANDSCAPE,
                WallParser
            ),
            ImageParser(
                WallHaven::class.java,
                API.WALL_BASE,
                category = API.CATE_WH_DARK,
                WallParser
            ),
            ImageParser(
                WallHaven::class.java,
                API.WALL_BASE,
                category = API.CATE_WH_SIMPLE,
                WallParser
            )
        )

        val YandeParsers = listOf(
            ImageParser(Yande::class.java, API.YANDE_BASE, parser = YandeParser)
        )
    }
}


interface IParser {
    fun parseImages(type: String, data: String): List<Image>
}
package com.danteandroi.composewall

import com.danteandroi.composewall.data.ImageParser
import com.danteandroi.composewall.data.parser.BcoderssParser
import com.danteandroi.composewall.data.parser.ManiaParser
import com.danteandroi.composewall.data.parser.WallParser
import com.danteandroi.composewall.data.parser.YandeParser
import com.danteandroi.composewall.net.*

/**
 * @author Du Wenyu
 * 2022/7/29
 */

data class MenuItem(val tabs: List<ImageParser>) {
    val name: String = tabs.first().apiClazz.simpleName

    companion object {
        private val wallParsers = API.wallHavenCategories.map { category ->
            ImageParser(WallHaven::class.java, API.WALL_BASE, category, WallParser)
        }
        private val yandeParsers = listOf(
            ImageParser(Yande::class.java, API.YANDE_BASE, category = "yande", parser = YandeParser)
        )
        private val maniaParsers = API.maniaCategories.map { category ->
            ImageParser(Mania::class.java, API.MANIA_BASE, category, ManiaParser)
        }
        private val bcoderssCategories = API.bcoderssCategories.map { category ->
            ImageParser(Bcoderss::class.java, API.BCODERSS_BASE, category, BcoderssParser)
        }

        val MainMenus = listOf(
            MenuItem(bcoderssCategories),
            MenuItem(maniaParsers),
            MenuItem(wallParsers),
            MenuItem(yandeParsers),
        )
    }

}




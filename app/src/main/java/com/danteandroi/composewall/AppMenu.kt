package com.danteandroi.composewall

import com.danteandroi.composewall.data.ImageParser

/**
 * @author Du Wenyu
 * 2022/7/29
 */

data class MenuItem(val tabs: List<ImageParser>) {
    val name: String = tabs.first().apiClazz.simpleName

    companion object {
        val MainMenus = listOf(MenuItem(ImageParser.WallParsers), MenuItem(ImageParser.YandeParsers))
    }
}




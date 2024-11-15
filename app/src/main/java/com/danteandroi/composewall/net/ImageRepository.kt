package com.danteandroi.composewall.net

import com.danteandroi.composewall.data.IParser
import com.danteandroi.composewall.data.Image
import com.danteandroi.composewall.data.parser.BcoderssParser
import com.danteandroi.composewall.data.parser.WallParser
import com.danteandroi.composewall.data.parser.YandeParser
import com.danteandroi.composewall.net.WallHaven.Companion.BETTER_RESOLUTION
import com.danteandroi.composewall.net.WallHaven.Companion.WALL_HAVEN_RATIOS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Dante
 * 2022/7/29
 */
object ImageRepository {
    suspend fun fetchImages(
        apiClazz: Class<*>,
        baseUrl: String,
        category: String,
        page: Int
    ): List<Image> {
        return withContext(Dispatchers.IO) {
            when (apiClazz) {
                WallHaven::class.java -> {
                    val isRandom = category == WallHaven.SORT_RANDOM
                    val result =
                        NetService.getInstance().createApi<WallHaven>(baseUrl).getWalls(
                            type = if (isRandom) "" else category,
                            ratios = WALL_HAVEN_RATIOS,
                            atLeast = BETTER_RESOLUTION,
                            page = if (isRandom && page == 1) null else page,
                            sort = WallHaven.SORT_RANDOM,
                            seed = WallParser.getSeed(category)
                        )
                    getImages(
                        WallParser.category(category),
                        apiClazz.simpleName,
                        result.string()
                    )
                }
                Yande::class.java -> {
                    val result = NetService.getInstance().createApi<Yande>(baseUrl)
                        .getYande(page)
                    getImages(
                        YandeParser,
                        apiClazz.simpleName,
                        result.string()
                    )
                }
                Bcoderss::class.java -> {
                    val result = NetService.getInstance().createApi<Bcoderss>(baseUrl)
                        .getPosters(if (category.isEmpty()) category else "tag/$category", page)
                    getImages(
                        BcoderssParser,
                        apiClazz.simpleName,
                        result.string()
                    )
                }
                else -> throw IllegalStateException("${apiClazz.name} not implemented in ${javaClass.canonicalName}")
            }
        }
    }

    private suspend fun getImages(parser: IParser, name: String, data: String): List<Image> {
        return parser.parseImages(name, data)
    }


}
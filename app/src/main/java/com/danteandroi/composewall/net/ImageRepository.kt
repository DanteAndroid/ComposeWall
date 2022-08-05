package com.danteandroi.composewall.net

import com.danteandroi.composewall.data.IParser
import com.danteandroi.composewall.data.Image
import com.danteandroi.composewall.data.parser.BcoderssParser
import com.danteandroi.composewall.data.parser.ManiaParser
import com.danteandroi.composewall.data.parser.WallParser
import com.danteandroi.composewall.data.parser.YandeParser
import com.danteandroi.composewall.net.WallHaven.Companion.AT_LEAST_RESOLUTION
import com.danteandroi.composewall.net.WallHaven.Companion.BETTER_RESOLUTION
import com.danteandroi.composewall.net.WallHaven.Companion.WALL_HAVEN_PORTRAIT_RATIOS
import com.danteandroi.composewall.net.WallHaven.Companion.WALL_HAVEN_RATIOS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Du Wenyu
 * 2022/7/29
 */
object ImageRepository {

    @Suppress("BlockingMethodInNonBlockingContext")
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
                            ratios = if (isRandom) WALL_HAVEN_PORTRAIT_RATIOS else WALL_HAVEN_RATIOS,
                            atLeast = if (isRandom) BETTER_RESOLUTION else AT_LEAST_RESOLUTION,
                            page = page,
                            sort = if (isRandom) WallHaven.SORT_RANDOM else WallHaven.SORT_RELEVANCE
                        )
                    getImages(
                        WallParser,
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
                Mania::class.java -> {
                    val result = NetService.getInstance().createApi<Mania>(baseUrl)
                        .getPosters(category, (page - 1) * 30)
                    getImages(
                        ManiaParser,
                        apiClazz.simpleName,
                        result.string()
                    )
                }
                Bcoderss::class.java -> {
                    val result = NetService.getInstance().createApi<Bcoderss>(baseUrl)
                        .getPosters(category, page)
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
package com.danteandroi.composewall.net

import com.danteandroi.composewall.data.Image
import com.danteandroi.composewall.data.ImageParser
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
    suspend fun fetchImages(imageParser: ImageParser, page: Int): List<Image> {
        return withContext(Dispatchers.IO) {
            when (imageParser.apiClazz) {
                WallHaven::class.java -> {
                    val isRandom = imageParser.category == WallHaven.SORT_RANDOM
                    val result =
                        NetService.getInstance().createApi<WallHaven>(imageParser.baseUrl).getWalls(
                            type = if (isRandom) "" else imageParser.category,
                            ratios = if (isRandom) WALL_HAVEN_PORTRAIT_RATIOS else WALL_HAVEN_RATIOS,
                            atLeast = if (isRandom) BETTER_RESOLUTION else AT_LEAST_RESOLUTION,
                            page = page,
                            sort = if (isRandom) WallHaven.SORT_RANDOM else WallHaven.SORT_RELEVANCE
                        )
                    getImages(
                        imageParser,
                        result.string()
                    )
                }
                Yande::class.java -> {
                    val result = NetService.getInstance().createApi<Yande>(imageParser.baseUrl)
                        .getYande(page)
                    getImages(imageParser, result.string())
                }
                Mania::class.java -> {
                    val result = NetService.getInstance().createApi<Mania>(imageParser.baseUrl)
                        .getPosters(imageParser.category, (page - 1) * 30)
                    getImages(imageParser, result.string())
                }
                Bcoderss::class.java -> {
                    val result = NetService.getInstance().createApi<Bcoderss>(imageParser.baseUrl)
                        .getPosters(imageParser.category, page)
                    getImages(imageParser, result.string())
                }
                else -> throw IllegalStateException("${imageParser.apiClazz.name} not implemented in ${javaClass.canonicalName}")
            }
        }
    }


    private suspend fun getImages(parser: ImageParser, data: String): List<Image> {
        return parser.parser.parseImages(parser.apiClazz.simpleName, data)
    }


}
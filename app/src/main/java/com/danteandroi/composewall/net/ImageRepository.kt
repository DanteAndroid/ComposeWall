package com.danteandroi.composewall.net

import com.danteandroi.composewall.data.parser.IParser
import com.danteandroi.composewall.data.Image
import com.danteandroi.composewall.data.parser.BcoderssParser
import com.danteandroi.composewall.data.parser.WallParser
import com.danteandroi.composewall.data.parser.YandeParser
import com.danteandroi.composewall.net.WallHaven.Companion.BETTER_RESOLUTION
import com.danteandroi.composewall.net.WallHaven.Companion.WALL_HAVEN_RATIOS
import com.danteandroi.composewall.utils.parse
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
                    NetService.getInstance().createApi<WallHaven>(baseUrl)
                        .getWalls(
                            type = if (isRandom) "" else category,
                            ratios = WALL_HAVEN_RATIOS,
                            atLeast = BETTER_RESOLUTION,
                            page = if (isRandom && page == 1) null else page,
                            sort = WallHaven.SORT_RANDOM,
                            seed = WallParser.getSeed(category)
                        ).parse(apiClazz.simpleName, WallParser.category(category))
                }

                Yande::class.java -> {
                     NetService.getInstance().createApi<Yande>(baseUrl)
                        .getYande(page)
                        .parse(apiClazz.simpleName,YandeParser)
                }

                Bcoderss::class.java -> {
                    NetService.getInstance().createApi<Bcoderss>(baseUrl)
                        .getPosters(if (category.isEmpty()) category else "tag/$category", page)
                        .parse(apiClazz.simpleName,BcoderssParser)
                }

                else -> throw IllegalStateException("${apiClazz.name} not implemented in ${javaClass.canonicalName}")
            }
        }
    }

}
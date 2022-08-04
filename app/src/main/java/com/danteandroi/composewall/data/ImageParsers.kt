package com.danteandroi.composewall.data

import coil.imageLoader
import coil.request.ImageRequest
import com.danteandroi.composewall.MainActivity

/**
 * @author Du Wenyu
 * 2022/7/29
 */
class ImageParser(
    val apiClazz: Class<*>,
    val baseUrl: String,
    val category: String = "",
    val parser: IParser
)

interface IParser {

    suspend fun parseImages(type: String, data: String): List<Image>

    suspend fun preloadImage(thumbUrl: String): IntArray {
        val array = IntArray(2)
        MainActivity.context?.let {
            val request = ImageRequest.Builder(it)
                .data(thumbUrl)
                .build()
            val result = it.imageLoader.execute(request)
            result.drawable?.let { drawable ->
                array[0] = drawable.intrinsicWidth
                array[1] = drawable.intrinsicHeight
            }
        }
        return array
    }

}
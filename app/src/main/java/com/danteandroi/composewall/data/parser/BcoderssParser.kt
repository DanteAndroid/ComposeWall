package com.danteandroi.composewall.data.parser

import com.danteandroi.composewall.data.IParser
import com.danteandroi.composewall.data.Image
import org.jsoup.Jsoup
import java.io.IOException

/**
 * @author Dante
 * 2019-09-05
 */
object BcoderssParser : IParser {

    override suspend fun parseImages(type: String, data: String): List<Image> {
        val images = arrayListOf<Image>()
        val document = Jsoup.parse(data)
        val elements = document.select("ul[class=wallpaper] li")
        for (element in elements) {
            try {
                val url = element.selectFirst("img")?.attr("src")
                if (url.isNullOrBlank()) continue
                val originalUrl = getOriginalUrl(url)
                val refer = element.selectFirst("a")?.attr("href").orEmpty()
                images.add(
                    Image(
                        id = url.substringAfterLast("/"),
                        thumbnail = url,
                        url = originalUrl,
                        type = type,
                        refer = refer
                    )
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return images
    }

    private fun getOriginalUrl(thumbUrl: String): String {
        val end = "-${thumbUrl.substringAfterLast("-")}"
        return thumbUrl.replace(end, ".jpg")
    }

}
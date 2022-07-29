package com.danteandroi.composewall.data.parser

import com.danteandroi.composewall.data.IParser
import com.danteandroi.composewall.data.Image
import org.jsoup.Jsoup
import java.io.IOException

/**
 * @author Du Wenyu
 * 2019-09-05
 */
object YandeParser : IParser {

    override fun parseImages(type: String, data: String): List<Image> {
        val images = arrayListOf<Image>()
        val document = Jsoup.parse(data)
        val elements = document.select("ul[id=post-list-posts] li")
        for (element in elements) {
            try {
                val a = element.selectFirst("a[class=thumb]")
                val original = element.selectFirst("a[class=directlink largeimg]")
                val originalUrl = original?.attr("href")
                if (originalUrl.isNullOrBlank()) continue
                val refer = a?.attr("href") ?: ""
                val thumbUrl = a?.selectFirst("img")?.attr("src") ?: originalUrl
                images.add(
                    Image(
                        id = originalUrl,
                        thumbnail = thumbUrl,
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

}
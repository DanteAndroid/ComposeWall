package com.danteandroi.composewall.data.parser

import com.danteandroi.composewall.data.Image
import org.jsoup.Jsoup
import java.io.IOException

/**
 * @author Dante
 * 2019-09-03
 */
data object WallParser : IParser {

    private var category: String = ""
    private val cachedMap = hashMapOf<String, String>()

    fun getSeed(key: String) = cachedMap[key]

    override suspend fun parseImages(type: String, data: String): List<Image> {
        val images = arrayListOf<Image>()
        val document = Jsoup.parse(data)
        val elements = document.select("div[id=thumbs] figure")
        runCatching {
            // 提取 seed 值
            val seed = document
                .select("ul.pagination")
                .attr("data-pagination")
                .substringAfter("seed=")
                .substringBefore("&")
            if (category.isNotEmpty() && seed.isNotEmpty()) {
                cachedMap[category] = seed
            }
        }
        for (element in elements) {
            try {
                val img = element.selectFirst("img")
                val url = img?.attr("data-src")
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
        val id = thumbUrl.substringAfterLast("/").substringBefore(".")
        val tail = thumbUrl.substringAfterLast("small").replace(id, "wallhaven-$id")
        return "https://w.wallhaven.cc/full${tail}"
    }

    fun category(category: String): IParser {
        this.category = category
        return this
    }
}
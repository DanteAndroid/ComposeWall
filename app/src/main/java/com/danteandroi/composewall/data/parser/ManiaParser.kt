package com.danteandroi.composewall.data.parser

import com.danteandroi.composewall.data.IParser
import com.danteandroi.composewall.data.Image
import org.json.JSONArray
import org.jsoup.Jsoup
import java.io.IOException

/**
 * @author Du Wenyu
 * 2019-09-05
 */
object ManiaParser : IParser {

    override suspend fun parseImages(type: String, data: String): List<Image> {
        val images = arrayListOf<Image>()
        val document = Jsoup.parse(data)
        val elements = document.select("a[class=wallpaper image-should-set]")
        for (element in elements) {
            try {
                val thumbnailArray = element.attr("data-images-urls")
                val title = element.select("div[class=title]").first()?.text()
                if (thumbnailArray.isNullOrBlank() || title.isNullOrBlank()) continue
                val jsonArray = JSONArray(thumbnailArray)
                val urlJson = jsonArray.optJSONObject(0)
                if (urlJson == null || urlJson.optString("url").isEmpty()) continue
                val thumbnail = "https://" + urlJson.getString("url").removePrefix("//")
                val originalUrl = getOriginalUrl(thumbnail, title)
                val refer = element.selectFirst("a")?.attr("href").orEmpty()
                images.add(
                    Image(
                        id = thumbnail.substringAfterLast("/phone/").replace("/", "-"),
                        thumbnail = thumbnail,
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

    private fun getOriginalUrl(url: String, movieName: String): String {
        // https://wallpapers.moviemania.io/phone/movie/297762/431052/wonder-woman-phone-wallpaper.jpg?w=1536&h=2732
        val suffix = "." + url.substringAfterLast(".")
        return url.replaceFirst("thumbnails", "wallpapers").substringBeforeLast("/") +
                "/" + movieName.lowercase().replace(":", "").replace(" ", "-") +
                "-phone-wallpaper$suffix" + "?w=1536&h=2732"
            .removePrefix("//")
    }

}
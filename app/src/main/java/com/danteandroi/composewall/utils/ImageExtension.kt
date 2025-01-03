package com.danteandroi.composewall.utils

import android.content.Context
import android.graphics.drawable.Drawable
import coil.imageLoader
import coil.request.ImageRequest
import com.danteandroi.composewall.data.Image
import com.danteandroi.composewall.data.parser.IParser
import okhttp3.ResponseBody

/**
 * @author Dante
 * 2022/8/3
 */

fun String.alternativeImageUrl(): String {
    if (this.endsWith(".jpg")) {
        return this.replace(".jpg", ".png")
    }
    if (this.endsWith(".jpeg")) {
        return this.replace(".jpeg", ".png")
    }
    if (this.endsWith(".png")) {
        return this.replace(".png", ".jpg")
    }
    return this
}

fun String.removeBraces(): String {
    return this.replace("(", "").replace(")", "")
}

suspend fun Context?.preloadImage(url: String): Drawable? {
    return this?.let {
        val request = ImageRequest.Builder(it)
            .data(url)
            .build()
        it.imageLoader.execute(request).drawable
    }
}

suspend fun ResponseBody.parse(type: String, iParser: IParser): List<Image> {
    return iParser.parseImages(type, this.string())
}
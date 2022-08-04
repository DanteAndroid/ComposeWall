package com.danteandroi.composewall.utils

/**
 * @author Du Wenyu
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
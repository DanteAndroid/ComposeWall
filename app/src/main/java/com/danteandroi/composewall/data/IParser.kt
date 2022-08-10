package com.danteandroi.composewall.data

interface IParser {

    suspend fun parseImages(type: String, data: String): List<Image>

}
package com.danteandroi.composewall.data.parser

import com.danteandroi.composewall.data.Image

sealed interface IParser {

     suspend fun parseImages(type: String, data: String): List<Image>

}
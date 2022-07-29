package com.danteandroi.composewall.utils

import com.danteandroi.composewall.data.ImageParser
import com.danteandroi.composewall.net.ImageRepository
import com.danteandroi.composewall.net.ImageViewModel

/**
 * @author Du Wenyu
 * 2022/7/29
 */
object InjectionUtils {

    fun provideImageViewModel(parser: ImageParser) = ImageViewModel(provideImageRepository(parser))

    fun provideImageRepository(parser: ImageParser) = ImageRepository(parser)

}
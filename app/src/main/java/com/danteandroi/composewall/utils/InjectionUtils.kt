package com.danteandroi.composewall.utils

import com.danteandroi.composewall.net.ImageViewModel

/**
 * @author Du Wenyu
 * 2022/7/29
 */
object InjectionUtils {

    fun provideImageViewModel() = ImageViewModel()
//
//    fun provideImageRepository(parser: ImageParser) = ImageRepository(parser)

}
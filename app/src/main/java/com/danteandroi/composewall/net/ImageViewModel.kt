package com.danteandroi.composewall.net

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danteandroi.composewall.data.Image
import com.danteandroi.composewall.data.ImageParser
import com.danteandroi.composewall.data.ImageUiState
import com.danteandroi.composewall.data.LayoutType
import com.danteandroi.composewall.utils.safeLaunch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * @author Du Wenyu
 * 2022/7/29
 */
class ImageViewModel(private val imageRepository: ImageRepository = ImageRepository) : ViewModel() {

    val uiState = MutableStateFlow(ImageUiState())

    fun fetchImages(imageParser: ImageParser, page: Int = 1) {
        viewModelScope.safeLaunch {
            val result = imageRepository.fetchImages(imageParser, page)
            uiState.update {
                it.copy(
                    spanCount = if (imageParser.apiClazz == Yande::class.java) 3 else 2,
                    height = if (imageParser.apiClazz == Mania::class.java) 240 else 120,
                    images = it.images + result,
                    type = if (imageParser.apiClazz == Yande::class.java) LayoutType.Staggered else LayoutType.Fixed,
                )
            }
        }
    }

    fun findImage(id: String): Image? {
        return uiState.value.images.find { it.id == id }
    }

}
package com.danteandroi.composewall.net

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danteandroi.composewall.data.ImageParser
import com.danteandroi.composewall.data.ImageUiState
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
            uiState.update { it.copy(images = result) }
        }
    }

}
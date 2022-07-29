package com.danteandroi.composewall.net

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danteandroi.composewall.data.ImageParser
import com.danteandroi.composewall.data.ImageUiState
import com.danteandroi.composewall.utils.safeLaunch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author Du Wenyu
 * 2022/7/29
 */
class ImageViewModel(private val imageRepository: ImageRepository) : ViewModel() {

    val uiState = MutableStateFlow(ImageUiState())

    init {
        fetchImages()
    }

    fun fetchImages(page: Int = 1) {
        viewModelScope.safeLaunch {
            val result = imageRepository.fetchImages(page)
            uiState.update { it.copy(images = result) }
        }
    }

}
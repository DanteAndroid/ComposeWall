package com.danteandroi.composewall.net

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danteandroi.composewall.MenuItem
import com.danteandroi.composewall.data.Image
import com.danteandroi.composewall.data.UiState
import com.danteandroi.composewall.utils.safeLaunch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * @author Du Wenyu
 * 2022/7/29
 */
class ImageViewModel(private val imageRepository: ImageRepository = ImageRepository) : ViewModel() {

    val uiState = MutableStateFlow<UiState>(UiState.LoadingUiState)

    fun fetchImages(menuItem: MenuItem, index: Int, page: Int = 1) {
        viewModelScope.safeLaunch {
            val result = imageRepository.fetchImages(
                apiClazz = menuItem.apiClazz,
                baseUrl = menuItem.baseUrl,
                category = menuItem.category[index].first,
                page = page
            )
            uiState.update {
                if (it is UiState.SuccessUiState) {
                    it.copy(
                        config = menuItem.uiConfig,
                        images = it.images + result
                    )
                } else {
                    UiState.SuccessUiState(
                        config = menuItem.uiConfig,
                        images = result
                    )
                }
            }
        }
    }

    fun findImage(id: String): Image? {
        return (uiState.value as? UiState.SuccessUiState)?.images?.find { it.id == id }
    }

}
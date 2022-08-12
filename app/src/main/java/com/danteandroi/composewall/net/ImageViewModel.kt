package com.danteandroi.composewall.net

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danteandroi.composewall.MenuItem
import com.danteandroi.composewall.data.*
import com.danteandroi.composewall.utils.preloadImage
import com.danteandroi.composewall.utils.safeLaunch
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author Du Wenyu
 * 2022/7/29
 */
class ImageViewModel(private val imageRepository: ImageRepository = ImageRepository) : ViewModel() {

    val uiState = MutableStateFlow<UiState>(LoadingUiState)

    fun fetchImages(menuItem: MenuItem, index: Int, page: Int = 1) {
        if (uiState.value !is UiStateSuccess) {
            uiState.update { LoadingUiState }
        }
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            uiState.update {
                ErrorUiState
            }
        }) {
            val result = imageRepository.fetchImages(
                apiClazz = menuItem.apiClazz,
                baseUrl = menuItem.baseUrl,
                category = menuItem.category[index].first,
                page = page
            )
            uiState.update {
                if (it is UiStateSuccess) {
                    it.copy(
                        config = menuItem.uiConfig,
                        images = it.images + result
                    )
                } else {
                    UiStateSuccess(
                        config = menuItem.uiConfig,
                        images = result
                    )
                }
            }
        }
    }

    fun preloadImages(context: Context, menuItem: MenuItem, index: Int, page: Int = 1) {
        viewModelScope.safeLaunch {
            val result = imageRepository.fetchImages(
                apiClazz = menuItem.apiClazz,
                baseUrl = menuItem.baseUrl,
                category = menuItem.category[index].first,
                page = page
            )
            result.forEach {
                val drawables = context.preloadImage(it.url)
                Timber.d("preload image $drawables")
            }
        }
    }

    fun findImage(id: String): Image? {
        return (uiState.value as? UiStateSuccess)?.images?.find { it.id == id }
    }

}
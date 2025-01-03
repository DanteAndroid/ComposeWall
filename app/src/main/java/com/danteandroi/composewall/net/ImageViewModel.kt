package com.danteandroi.composewall.net

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danteandroi.composewall.MenuItem
import com.danteandroi.composewall.data.ErrorUiState
import com.danteandroi.composewall.data.Image
import com.danteandroi.composewall.data.LoadingUiState
import com.danteandroi.composewall.data.UiState
import com.danteandroi.composewall.data.UiStateSuccess
import com.danteandroi.composewall.utils.preloadImage
import com.danteandroi.composewall.utils.safeLaunch
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author Dante
 * 2022/7/29
 */
class ImageViewModel(private val imageRepository: ImageRepository = ImageRepository) : ViewModel() {

    val uiState = MutableStateFlow<UiState>(LoadingUiState)

    fun fetchImages(menuItem: MenuItem, index: Int, page: Int = 1) {
        uiState.update {
            if (it is UiStateSuccess) {
                it.copy(isLoading = true)
            } else {
                LoadingUiState
            }
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
            uiState.update { state ->
                if (state is UiStateSuccess && page > 1) {
                    state.copy(
                        config = menuItem.uiConfig,
                        images = (state.images + result),
                        isLoading = false
                    )
                } else {
                    UiStateSuccess(
                        config = menuItem.uiConfig,
                        images = result,
                        isLoading = false
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
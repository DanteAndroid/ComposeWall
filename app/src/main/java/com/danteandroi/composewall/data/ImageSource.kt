package com.danteandroi.composewall.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.danteandroi.composewall.MenuItem
import com.danteandroi.composewall.net.ImageRepository

/**
 * @author Du Wenyu
 * 2022/8/3
 */
class ImageSource(private val menuItem: MenuItem, private val repository: ImageRepository) :
    PagingSource<Int, Image>() {

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.pages[state.anchorPosition?.plus(1) ?: 0].prevKey
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val nextPage = params.key ?: 1
            val result = repository.fetchImages(
                apiClazz = menuItem.apiClazz,
                baseUrl = menuItem.baseUrl,
                category = menuItem.category[nextPage - 1].first,
                page = nextPage
            )
            LoadResult.Page(result, if (nextPage == 1) null else nextPage, nextPage + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}
package com.danteandroi.composewall.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danteandroi.composewall.MenuItem
import com.danteandroi.composewall.SafeMenus
import com.danteandroi.composewall.data.UiEvent
import com.danteandroi.composewall.net.ImageViewModel
import com.danteandroi.composewall.utils.EventManager
import kotlinx.coroutines.launch

/**
 * @author Dante
 * 2022/7/29
 */
@Composable
fun TabScreen(
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean = false,
    menuItem: MenuItem,
    onViewImage: (String, ImageViewModel) -> Unit = { _, _ -> }
) {
    Column(modifier.background(MaterialTheme.colorScheme.background)) {
        val coroutineScope = rememberCoroutineScope()
        val pagerState =
            androidx.compose.foundation.pager.rememberPagerState(pageCount = { menuItem.category.size })
        ScrollableTabRow(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 16.dp
        ) {
            menuItem.category.forEachIndexed { index, category ->
                Tab(
                    text = {
                        Text(
                            LocalContext.current.resources.getText(category.second)
                                .toString(),
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            if (pagerState.currentPage == index) {
                                EventManager.postEvent(UiEvent.ScrollToTop.name)
                            } else {
                                pagerState.scrollToPage(index)
                            }
                        }
                    }
                )
            }
        }
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) { pageIndex ->
            val viewModel: ImageViewModel =
                viewModel(key = pageIndex.toString() + menuItem.apiClazz.simpleName)
            var requestPage by rememberSaveable(pageIndex, menuItem) {
                mutableIntStateOf(1)
            }
            EventManager.Handler {
                if (it.name == UiEvent.Refresh.name) {
                    if (requestPage == 1) {
                        viewModel.fetchImages(menuItem, pageIndex, page = 1)
                    } else {
                        requestPage = 1
                    }
                }
            }
            LaunchedEffect(pageIndex, menuItem, requestPage) {
                viewModel.fetchImages(menuItem, pageIndex, requestPage)
//                if (menuItem.apiClazz == Yande::class.java) {
//                    viewModel.preloadImages(menuItem, page, requestPage)
//                }
            }
            val uiState by viewModel.uiState.collectAsState()
            ImageListScreen(
                modifier = Modifier.padding(top = 2.dp, start = 2.dp, end = 2.dp),
                isExpandedScreen = isExpandedScreen,
                uiState = uiState,
                onClickImage = {
                    onViewImage.invoke(it, viewModel)
                },
                onClickRetry = {
                    if (requestPage == 1) {
                        viewModel.fetchImages(menuItem, pageIndex, page = 1)
                    } else {
                        requestPage = 1
                    }
                }
            ) {
                requestPage++
            }
        }
    }
}

@Preview
@Composable
fun TabScreenPreview() {
    TabScreen(menuItem = SafeMenus.first())
}
package com.danteandroi.composewall.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danteandroi.composewall.MenuItem
import com.danteandroi.composewall.MenuItem.Companion.MainMenus
import com.danteandroi.composewall.data.UiEvent
import com.danteandroi.composewall.net.ImageViewModel
import com.danteandroi.composewall.utils.EventManager
import com.danteandroi.composewall.utils.InjectionUtils
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

/**
 * @author Du Wenyu
 * 2022/7/29
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabScreen(
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean = false,
    menuItem: MenuItem,
    onViewImage: (String, ImageViewModel) -> Unit = { _, _ -> }
) {
    Column(modifier.background(MaterialTheme.colorScheme.background)) {
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState()
        ScrollableTabRow(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                )
            }
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
            count = menuItem.category.size,
            state = pagerState
        ) { page ->
            val viewModel = remember(page, menuItem) {
                InjectionUtils.provideImageViewModel()
            }
            var requestPage by remember(page, menuItem) {
                mutableStateOf(1)
            }
            LaunchedEffect(page, menuItem, requestPage) {
                viewModel.fetchImages(menuItem, page, requestPage)
//                if (menuItem.apiClazz == Yande::class.java) {
//                    viewModel.preloadImages(menuItem, page, requestPage)
//                }
            }
            val uiState by viewModel.uiState.collectAsState()
            ImageListScreen(
                modifier = Modifier.padding(top = 6.dp, start = 6.dp, end = 6.dp),
                isExpandedScreen = isExpandedScreen,
                uiState = uiState,
                onClickImage = {
                    onViewImage.invoke(it, viewModel)
                },
                onClickRetry = {
                    viewModel.fetchImages(menuItem, page, requestPage)
                }
            ) {
                requestPage++
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TabScreenPreview() {
    TabScreen(menuItem = MainMenus.first())
}
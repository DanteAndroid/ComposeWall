package com.danteandroi.composewall.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danteandroi.composewall.MenuItem
import com.danteandroi.composewall.MenuItem.Companion.MainMenus
import com.danteandroi.composewall.net.ImageViewModel
import com.danteandroi.composewall.utils.InjectionUtils
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import java.util.Locale

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
    val tabs = menuItem.tabs
    Column(modifier) {
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState()
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                )
            }
        ) {
            tabs.forEachIndexed { index, imageParser ->
                Tab(
                    text = {
                        Text(imageParser.category.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        })
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            count = tabs.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val viewModel = remember(page, tabs) {
                InjectionUtils.provideImageViewModel()
            }
            var requestPage by remember(page, tabs) {
                mutableStateOf(1)
            }
            LaunchedEffect(page, tabs, requestPage) {
                viewModel.fetchImages(tabs[page], requestPage)
            }
            val uiState by viewModel.uiState.collectAsState()
            ImageListScreen(
                modifier = Modifier.padding(top = 6.dp, start = 6.dp, end = 6.dp),
                isExpandedScreen = isExpandedScreen,
                uiState = uiState,
                onViewImage = {
                    onViewImage.invoke(it, viewModel)
                },
                onScrollToBottom = {
                    requestPage++
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TabScreenPreview() {
    TabScreen(menuItem = MainMenus.first())
}
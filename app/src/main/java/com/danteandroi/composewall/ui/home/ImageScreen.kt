package com.danteandroi.composewall.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiBad
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.danteandroi.composewall.R
import com.danteandroi.composewall.data.ErrorUiState
import com.danteandroi.composewall.data.Image
import com.danteandroi.composewall.data.LayoutType
import com.danteandroi.composewall.data.LoadingUiState
import com.danteandroi.composewall.data.UiEvent
import com.danteandroi.composewall.data.UiState
import com.danteandroi.composewall.data.UiStateSuccess
import com.danteandroi.composewall.utils.EventManager
import com.danteandroi.composewall.utils.PRELOAD_COUNT
import com.danteandroi.composewall.utils.isScrolledToBottom
import com.danteandroi.composewall.widget.StaggeredVerticalGrid
import timber.log.Timber

/**
 * @author Dante
 * 2022/7/29
 */
@Composable
fun ImageListScreen(
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean = false,
    uiState: UiState,
    onClickImage: (String) -> Unit,
    onClickRetry: () -> Unit,
    onScrollToBottom: () -> Unit
) {
    when (uiState) {
        is UiStateSuccess -> {
            when (uiState.config.type) {
                LayoutType.Fixed -> {
                    val scrollState = rememberLazyGridState()
                    val scrollToBottom by remember {
                        derivedStateOf {
                            scrollState.isScrolledToBottom(PRELOAD_COUNT)
                        }
                    }
                    LaunchedEffect(scrollToBottom) {
                        if (scrollToBottom) onScrollToBottom.invoke()
                    }
                    EventManager.Handler(handleEvent = {
                        if (it.name == UiEvent.ScrollToTop.name) {
                            scrollState.scrollToItem(0)
                        }
                    })
                    LazyVerticalGrid(
                        modifier = modifier,
                        state = scrollState,
                        columns = GridCells.Fixed(
                            if (isExpandedScreen)
                                uiState.config.spanCount * 2 else uiState.config.spanCount
                        )
                    ) {
                        items(uiState.images.size, key = {
                            uiState.images[it].id + it
                        }) {
                            val image = uiState.images[it]
                            ImageItem(
                                modifier = Modifier.aspectRatio(uiState.config.aspectRatio),
                                onViewImage = onClickImage,
                                image = image
                            )
                        }
                    }
                }

                LayoutType.Staggered -> {
                    val scrollState = rememberScrollState()
                    StaggeredVerticalGrid(
                        modifier = modifier.verticalScroll(scrollState),
                        if (isExpandedScreen) 300.dp else 150.dp
                    ) {
                        uiState.images.forEach { image ->
                            ImageItem(onViewImage = onClickImage, image = image)
                        }
                        LoadMore(onScrollToBottom)
                    }
                }
            }
        }
        is LoadingUiState -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        is ErrorUiState -> {
            Column(
                Modifier
                    .clickable {
                        onClickRetry.invoke()
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(86.dp),
                    imageVector = Icons.Default.SignalWifiBad,
                    contentDescription = "Broken image"
                )
                Spacer(modifier = Modifier.width(36.dp))
                Text(
                    text = stringResource(id = R.string.tap_to_retry),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
        }
    }
}

@Composable
private fun LoadMore(onScrollToBottom: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(18.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 2.dp
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "Loading...")
        LaunchedEffect(Unit) {
            Timber.d("Scrolled to bottom")
            onScrollToBottom.invoke()
        }
    }
}

@Composable
private fun ImageItem(
    modifier: Modifier = Modifier,
    onViewImage: (String) -> Unit,
    image: Image
) {
    AsyncImage(
        modifier = modifier
            .clickable {
                onViewImage(image.id)
            }
            .fillMaxWidth()
            .padding(2.dp)
            .clip(RoundedCornerShape(2.dp)),
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(LocalContext.current)
            .data(image.thumbnail)
            .crossfade(true)
            .build(),
        contentDescription = "image"
    )
}


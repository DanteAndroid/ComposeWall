package com.danteandroi.composewall.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiBad
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.danteandroi.composewall.R
import com.danteandroi.composewall.data.*
import com.danteandroi.composewall.widget.StaggeredVerticalGrid
import timber.log.Timber


/**
 * @author Du Wenyu
 * 2022/7/29
 */
@Composable
fun ImageListScreen(
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean = false,
    uiState: UiState,
    onViewImage: (String) -> Unit,
    onRetry: () -> Unit,
    onScrollToBottom: () -> Unit
) {
    when (uiState) {
        is UiStateSuccess -> {
            when (uiState.config.type) {
                LayoutType.Fixed -> {
                    LazyVerticalGrid(
                        modifier = modifier,
                        state = rememberLazyGridState(),
                        columns = GridCells.Fixed(
                            if (isExpandedScreen)
                                uiState.config.spanCount * 2 else uiState.config.spanCount
                        )
                    ) {
                        items(uiState.images.size) {
                            val image = uiState.images[it]
                            ImageItem(
                                modifier = Modifier.aspectRatio(uiState.config.aspectRatio),
                                onViewImage = onViewImage,
                                image = image
                            )
                        }
                        item {
                            LoadMore(onScrollToBottom)
                        }
                    }
                }

                LayoutType.Staggered -> {
                    StaggeredVerticalGrid(
                        modifier = modifier.verticalScroll(rememberScrollState()),
                        if (isExpandedScreen) 300.dp else 150.dp
                    ) {
                        uiState.images.forEach { image ->
                            ImageItem(onViewImage = onViewImage, image = image)
                        }
                        LoadMore(onScrollToBottom)
                    }
                }
            }
        }
        is LoadingUiState -> {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        is ErrorUiState -> {
            Column(
                Modifier
                    .clickable {
                        onRetry.invoke()
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
//    Timber.d("ImageItem ${image.width} ${image.height}")
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


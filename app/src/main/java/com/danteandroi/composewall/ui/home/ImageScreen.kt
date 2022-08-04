package com.danteandroi.composewall.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.danteandroi.composewall.data.Image
import com.danteandroi.composewall.data.ImageUiState
import com.danteandroi.composewall.data.LayoutType
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
    uiState: ImageUiState,
    onViewImage: (String) -> Unit,
    onScrollToBottom: () -> Unit
) {
    if (uiState.images.isEmpty()) {
        CircularProgressIndicator()
    } else {
        when (uiState.type) {
            LayoutType.Fixed -> {
                LazyVerticalGrid(
                    modifier = modifier,
                    columns = GridCells.Fixed(
                        if (isExpandedScreen)
                            uiState.spanCount * 2 else uiState.spanCount
                    )
                ) {
                    items(uiState.images.size) {
                        val image = uiState.images[it]
                        ImageItem(
                            modifier = Modifier.height(uiState.height.dp),
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
}

@Composable
private fun LoadMore(onScrollToBottom: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(54.dp), Arrangement.Center
    ) {
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


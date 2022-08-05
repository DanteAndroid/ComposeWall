package com.danteandroi.composewall.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
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
import com.danteandroi.composewall.data.Image
import com.danteandroi.composewall.data.LayoutType
import com.danteandroi.composewall.data.UiState
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
    onScrollToBottom: () -> Unit
) {
    when (uiState) {
        is UiState.SuccessUiState -> {
            when (uiState.config.type) {
                LayoutType.Fixed -> {
                    LazyVerticalGrid(
                        modifier = modifier,
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
        is UiState.LoadingUiState -> {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        is UiState.ErrorUiState -> {
            Column(Modifier.clickable {

            }) {
                Image(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.BrokenImage,
                    contentDescription = "Broken image"
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = stringResource(id = R.string.tap_to_retry))
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


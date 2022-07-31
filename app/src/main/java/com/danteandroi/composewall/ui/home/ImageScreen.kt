package com.danteandroi.composewall.ui.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.danteandroi.composewall.data.ImageUiState

/**
 * @author Du Wenyu
 * 2022/7/29
 */
@Composable
fun ImageScreen(
    modifier: Modifier = Modifier,
    uiState: ImageUiState
) {
    if (uiState.images.isEmpty()) {
        CircularProgressIndicator()
    } else {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(uiState.spanCount), content = {
                items(uiState.images.size) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(80.dp, 180.dp)
                            .padding(2.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        contentScale = ContentScale.FillWidth,
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(uiState.images[it].thumbnail)
                            .crossfade(true)
                            .build(),
                        contentDescription = "image"
                    )
                }
            })
    }
}
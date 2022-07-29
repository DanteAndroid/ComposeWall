package com.danteandroi.composewall.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.danteandroi.composewall.R
import com.danteandroi.composewall.data.Image
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
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(uiState.spanCount), content = {
            items(uiState.images.size) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "logo"
                )
            }
        })

}
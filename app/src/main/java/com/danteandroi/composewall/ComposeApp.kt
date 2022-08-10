package com.danteandroi.composewall

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.danteandroi.composewall.ui.theme.ComposeWallTheme

/**
 * @author Du Wenyu
 * 2022/7/29
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComposeApp(sizeClass: WindowSizeClass? = null) {
    ComposeWallTheme {
        ComposeNavGraph(
            appState = rememberComposeAppState(),
            sizeClass = sizeClass
        )
    }
}

@Preview
@Composable
fun ComposeAppPreview() {
    ComposeApp()
}
package com.danteandroi.composewall

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.danteandroi.composewall.ui.component.BackdropScaffold
import com.danteandroi.composewall.ui.theme.ComposeWallTheme

/**
 * @author Du Wenyu
 * 2022/7/29
 */
@Composable
fun ComposeApp() {
    ComposeWallTheme {
        BackdropScaffold()
    }
}

@Preview
@Composable
fun ComposeAppPreview() {
    ComposeApp()
}
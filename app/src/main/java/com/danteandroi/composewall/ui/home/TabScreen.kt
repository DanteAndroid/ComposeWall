package com.danteandroi.composewall.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.danteandroi.composewall.MenuItem
import com.danteandroi.composewall.MenuItem.Companion.MainMenus
import androidx.compose.runtime.*
import com.danteandroi.composewall.utils.InjectionUtils

/**
 * @author Du Wenyu
 * 2022/7/29
 */
@Composable
fun TabScreen(
    modifier: Modifier = Modifier,
    menuItem: MenuItem
) {
    var currentTab by remember {
        mutableStateOf(menuItem.tabs.first())
    }
    val viewModel by remember {
        derivedStateOf { InjectionUtils.provideImageViewModel(currentTab) }
    }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            menuItem.tabs.forEach {
                Text(text = it.category, modifier = Modifier.clickable {
                    currentTab = it
                })
            }
        }
        ImageScreen(modifier = modifier.fillMaxSize(), uiState = uiState)
    }
}

@Preview(showBackground = true)
@Composable
fun TabScreenPreview() {
    TabScreen(menuItem = MainMenus.first())
}
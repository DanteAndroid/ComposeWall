package com.danteandroi.composewall.ui.component

import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.danteandroi.composewall.MenuItem.Companion.MainMenus
import com.danteandroi.composewall.ui.home.TabScreen
import kotlinx.coroutines.launch

/**
 * @author Du Wenyu
 * 2022/7/29
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackdropScaffold(
    modifier: Modifier = Modifier
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    var currentMenu by remember {
        mutableStateOf(0)
    }
    val coroutine = rememberCoroutineScope()
    fun setMenuVisible(visible: Boolean) {
        coroutine.launch {
            if (visible) {
                scaffoldState.reveal()
            } else {
                scaffoldState.conceal()
            }
        }
    }

    BackdropScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        appBar = {
            BackdropTitle(onTitleClick = {
                setMenuVisible(true)
            })
        },
        backLayerContent = {
            BackdropMenu(
                menus = MainMenus,
                onMenuSelected = {
                    setMenuVisible(false)
                    currentMenu = it
                }
            )
        },
        frontLayerContent = {
            TabScreen(menuItem = MainMenus[currentMenu])
        }
    )
}

@Preview
@Composable
fun ComposeBackdropScaffoldPreview() {
    BackdropScaffold()
}
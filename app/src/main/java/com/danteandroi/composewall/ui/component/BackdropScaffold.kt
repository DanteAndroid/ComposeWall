package com.danteandroi.composewall.ui.component

import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.danteandroi.composewall.MenuItem.Companion.MainMenus
import com.danteandroi.composewall.net.ImageViewModel
import com.danteandroi.composewall.ui.home.TabScreen
import kotlinx.coroutines.launch

/**
 * @author Du Wenyu
 * 2022/7/29
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackdropScaffold(
    modifier: Modifier = Modifier,
    scaffoldState: BackdropScaffoldState,
    isExpandedScreen: Boolean = false,
    onViewImage: (String, ImageViewModel) -> Unit = { _, _ -> }
) {
    var currentMenu by remember {
        mutableStateOf(0)
    }
    val coroutine = rememberCoroutineScope()
    BackdropScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        gesturesEnabled = true,
        backLayerBackgroundColor = MaterialTheme.colorScheme.primary,
        appBar = {
            BackdropTitle(
                isExpandedScreen = isExpandedScreen,
                onTitleClick = {
                    coroutine.launch {
                        if (scaffoldState.isConcealed) {
                            scaffoldState.reveal()
                        } else {
                            scaffoldState.conceal()
                        }
                    }
                })
        },
        backLayerContent = {
            BackdropMenu(
                menus = MainMenus,
                isExpandedScreen = isExpandedScreen,
                onMenuSelected = {
                    coroutine.launch {
                        scaffoldState.conceal()
                    }
                    currentMenu = it
                }
            )
        },
        frontLayerContent = {
            TabScreen(
                menuItem = MainMenus[currentMenu],
                isExpandedScreen = isExpandedScreen,
                onViewImage = onViewImage
            )
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ComposeBackdropScaffoldPreview() {
    BackdropScaffold(scaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed))
}
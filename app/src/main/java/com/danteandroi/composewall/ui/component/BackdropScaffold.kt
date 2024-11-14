package com.danteandroi.composewall.ui.component

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BackdropScaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BackdropScaffoldState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BackdropValue
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.danteandroi.composewall.MenuItem.Companion.AllMenus
import com.danteandroi.composewall.MenuItem.Companion.SafeMenus
import com.danteandroi.composewall.net.ImageViewModel
import com.danteandroi.composewall.ui.home.TabScreen
import com.danteandroi.composewall.utils.SecretModeUtil
import kotlinx.coroutines.launch

/**
 * @author Dante
 * 2022/7/29
 */
@Composable
fun BackdropScaffold(
    modifier: Modifier = Modifier,
    scaffoldState: BackdropScaffoldState,
    isExpandedScreen: Boolean = false,
    onViewImage: (String, ImageViewModel) -> Unit = { _, _ -> }
) {
    val menus = if (SecretModeUtil.isSecretMode()) AllMenus else SafeMenus
    var currentMenu by remember {
        mutableIntStateOf(0)
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
                        if (isExpandedScreen) {
                            SecretModeUtil.onClick()
                        }
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
                menus = menus,
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
                menuItem = menus[currentMenu],
                isExpandedScreen = isExpandedScreen,
                onViewImage = onViewImage
            )
        }
    )
}

@Preview
@Composable
fun ComposeBackdropScaffoldPreview() {
    BackdropScaffold(scaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed))
}
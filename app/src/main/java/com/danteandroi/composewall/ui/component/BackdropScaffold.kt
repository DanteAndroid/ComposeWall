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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danteandroi.composewall.AllMenus
import com.danteandroi.composewall.SafeMenus
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
    var currentMenu by rememberSaveable {
        mutableIntStateOf(0)
    }
    val coroutine = rememberCoroutineScope()
    BackdropScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        gesturesEnabled = true,
        backLayerBackgroundColor = MaterialTheme.colorScheme.primary,
        peekHeight = 38.dp,
        appBar = {
            BackdropTitle(
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
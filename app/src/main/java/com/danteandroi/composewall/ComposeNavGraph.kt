package com.danteandroi.composewall

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.danteandroi.composewall.data.Image
import com.danteandroi.composewall.ui.component.BackdropScaffold
import com.danteandroi.composewall.ui.detail.DetailScreen
import com.danteandroi.composewall.utils.isExpandedScreen

/**
 * @author Du Wenyu
 * 2022/8/2
 */
@Composable
fun ComposeNavGraph(
    modifier: Modifier = Modifier,
    appState: ComposeAppState,
    sizeClass: WindowSizeClass? = null,
    startDestination: String = ComposeDestinations.HOME
) {
    NavHost(
        modifier = modifier,
        startDestination = startDestination,
        navController = appState.navController
    ) {
        composable(ComposeDestinations.HOME) { stackEntry ->
            var currentDetailImage by remember {
                mutableStateOf(Image.EMPTY)
            }
            Box(modifier = modifier) {
                BackdropScaffold(
                    modifier = Modifier.statusBarsPadding(),
                    sizeClass.isExpandedScreen()
                ) { id, vm ->
                    vm.findImage(id)?.let { image ->
                        currentDetailImage = image
                    }
                }
                AnimatedVisibility(
                    currentDetailImage.isValidImage,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    DetailScreen(
                        image = currentDetailImage,
                        navigateUp = { currentDetailImage = Image.EMPTY })
                }
            }
        }
        // unused destination
        composable(
            "${ComposeDestinations.DETAIL}/{${ComposeDestinations.DETAIL_ID}}",
            arguments = listOf(navArgument(ComposeDestinations.DETAIL_ID) {
                type = NavType.StringType
            })
        ) {
            val id = requireNotNull(it.arguments).getString(ComposeDestinations.DETAIL_ID)!!
            appState.currentViewModel?.findImage(id)?.let { image ->
                DetailScreen(image = image, navigateUp = { appState.navigateUp() })
            }
        }
    }
}
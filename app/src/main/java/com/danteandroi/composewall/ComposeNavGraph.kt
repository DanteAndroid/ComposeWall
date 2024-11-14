package com.danteandroi.composewall

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BackdropValue
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.danteandroi.composewall.ui.component.BackdropScaffold
import com.danteandroi.composewall.ui.detail.DetailScreen
import com.danteandroi.composewall.utils.isExpandedScreen

/**
 * @author Dante
 * 2022/8/2
 */
@Composable
fun ComposeNavGraph(
    modifier: Modifier = Modifier,
    appState: ComposeAppState,
    sizeClass: WindowSizeClass? = null
) {
    NavHost(
        modifier = modifier,
        startDestination = Destinations.Home,
        navController = appState.navController
    ) {
        composable<Destinations.Home> {
            Box(modifier = modifier) {
                BackdropScaffold(
                    modifier = Modifier.statusBarsPadding(),
                    scaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed),
                    sizeClass.isExpandedScreen()
                ) { id, vm ->
                    appState.navigateToImage(id, vm)
                }
            }
        }
        composable<Destinations.Detail> { backstackEntry ->
            val detail = backstackEntry.toRoute<Destinations.Detail>()
            appState.currentViewModel?.findImage(detail.id)?.let { image ->
                Scaffold(snackbarHost = { SnackbarHost(hostState = appState.scaffoldState) }) { paddings ->
                    DetailScreen(
                        Modifier.padding(paddings),
                        image = image,
                        navigateUp = {
                            appState.navigateUp()
                        })
                }
            }
        }
    }
}
package com.danteandroi.composewall

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.danteandroi.composewall.net.ImageViewModel
import com.danteandroi.composewall.utils.SnackBarManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author Du Wenyu
 * 2022/8/2
 */
object ComposeDestinations {

    const val HOME = "home"
    const val DETAIL_ID = "id"
    const val DETAIL = "detail"

}

@Composable
fun rememberComposeAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    snackBarManager: SnackBarManager = SnackBarManager,
    navController: NavHostController = rememberNavController(),
    resources: Resources = LocalContext.current.resources,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState, navController) {
    ComposeAppState(scaffoldState, snackBarManager, resources, navController, coroutineScope)
}

@Stable
class ComposeAppState(
    private val scaffoldState: ScaffoldState,
    private val snackBarManager: SnackBarManager,
    private val resources: Resources,
    val navController: NavHostController,
    coroutineScope: CoroutineScope
) {

    init {
        coroutineScope.launch {
            snackBarManager.messages.collect { messages ->
                try {
                    if (messages.isNotEmpty()) {
                        val text = resources.getText(messages[0].message)
                        scaffoldState.snackbarHostState.showSnackbar(message = text.toString())
                        snackBarManager.setMessageShown(messages[0].id)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    var currentViewModel: ImageViewModel? = null
        private set

    val currentRoute: String? get() = navController.currentDestination?.route

    fun navigateUp() = navController.navigateUp()

    fun navigateToHomeRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToDetail(id: String, viewModel: ImageViewModel, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            currentViewModel = viewModel
            navController.navigate("${ComposeDestinations.DETAIL}/$id") {
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    private fun NavBackStackEntry.lifecycleIsResumed() =
        this.lifecycle.currentState == Lifecycle.State.RESUMED
}
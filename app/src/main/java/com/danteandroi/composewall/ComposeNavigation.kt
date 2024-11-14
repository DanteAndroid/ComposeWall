package com.danteandroi.composewall

import android.content.res.Resources
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.danteandroi.composewall.net.ImageViewModel
import com.danteandroi.composewall.utils.SnackBarManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

/**
 * @author Dante
 * 2022/8/2
 */
sealed class Destinations {
    @Serializable
    object Home

    @Serializable
    data class Detail(val id: String)
}


@Composable
fun rememberComposeAppState(
    scaffoldState: SnackbarHostState = SnackbarHostState(),
    snackBarManager: SnackBarManager = SnackBarManager,
    navController: NavHostController = rememberNavController(),
    resources: Resources = LocalContext.current.resources,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState, navController) {
    ComposeAppState(scaffoldState, snackBarManager, resources, navController, coroutineScope)
}

@Stable
class ComposeAppState(
    val scaffoldState: SnackbarHostState,
    private val snackBarManager: SnackBarManager,
    private val resources: Resources,
    val navController: NavHostController,
    coroutineScope: CoroutineScope
) {

    init {
        coroutineScope.launch {
            snackBarManager.messages.collect { messages ->
                if (messages.isNotEmpty()) {
                    val text = resources.getText(messages[0].message)
                    scaffoldState.showSnackbar(message = text.toString())
                    snackBarManager.setMessageShown(messages[0].id)
                }
            }
        }
    }

    var currentViewModel: ImageViewModel? = null
        private set

    fun navigateUp() = navController.navigateUp()

    fun navigateToImage(id: String, viewModel: ImageViewModel) {
        currentViewModel = viewModel
        navController.navigate(route = Destinations.Detail(id))
    }

}
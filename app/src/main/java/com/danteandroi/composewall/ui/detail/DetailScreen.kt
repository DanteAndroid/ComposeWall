package com.danteandroi.composewall.ui.detail

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.IntentUtils
import com.danteandroi.composewall.BuildConfig
import com.danteandroi.composewall.R
import com.danteandroi.composewall.data.Image
import com.danteandroi.composewall.data.ImageDetailState
import com.danteandroi.composewall.ui.component.OptionsDialog
import com.danteandroi.composewall.utils.SnackBarManager
import com.danteandroi.composewall.utils.alternativeImageUrl
import com.danteandroi.composewall.utils.preloadImage
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author Du Wenyu
 * 2022/8/2
 */
const val SCALE_ANIMATION_DURATION = 300

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    image: Image,
    navigateUp: () -> Unit,
    snackBarManager: SnackBarManager = SnackBarManager
) {
    BackHandler {
        navigateUp.invoke()
    }
    var loading by remember {
        mutableStateOf(true)
    }
    var detailState by remember {
        mutableStateOf(ImageDetailState(false, image.thumbnail))
    }
    var showOptions by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    if (showOptions) {
        val context = LocalContext.current
        val appName = stringResource(id = R.string.app_name)
        OptionsDialog(
            onDismissRequest = {
                showOptions = false
            },
            optionsArray = arrayOf(
                Icons.Default.Download to R.string.save_picture,
                Icons.Default.Share to R.string.share
            ),
            onItemClick = { index ->
                when (index) {
                    0 -> scope.launch {
                        context.preloadImage(image.url)?.let { drawable ->
                            val file = ImageUtils.save2Album(
                                drawable.toBitmap(),
                                appName,
                                Bitmap.CompressFormat.JPEG
                            )
                            if (file?.exists() == true) {
                                snackBarManager.showMessages(R.string.save_picture_success)
                            } else {
                                snackBarManager.showMessages(R.string.save_picture_failed)
                                context.startActivity(
                                    IntentUtils.getLaunchAppDetailsSettingsIntent(
                                        BuildConfig.APPLICATION_ID
                                    )
                                )
                            }
                        }
                    }
                    1 -> scope.launch {
                        context.preloadImage(image.url)?.let { drawable ->
                            val file = ImageUtils.save2Album(
                                drawable.toBitmap(),
                                appName,
                                Bitmap.CompressFormat.JPEG
                            )
                            context.startActivity(
                                IntentUtils.getShareImageIntent(
                                    file
                                )
                            )
                        }
                    }
                }
            }
        )
    }
    Box(
        modifier = modifier
            .clickable {
                navigateUp.invoke()
            }
            .background(Color.Black.copy(alpha = 0.85f))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
        LaunchedEffect(key1 = Unit, block = {
            scope.launch {
                Timber.d("ImageRequest: ${image.url}")
                context.imageLoader.execute(
                    ImageRequest.Builder(context).data(image.url)
                        .listener(onError = { _, _ ->
                            detailState =
                                ImageDetailState(true, image.url.alternativeImageUrl())
                            Timber.d("ImageRequest onError $detailState")
                        }, onSuccess = { _, _ ->
                            detailState = ImageDetailState(true, image.url)
                            Timber.d("ImageRequest onSuccess $detailState")
                        }).build()
                )
            }
        })
        AnimatedContent(detailState, transitionSpec = {
            fadeIn(animationSpec = tween(SCALE_ANIMATION_DURATION)) +
                    scaleIn(
                        initialScale = 0.92f,
                        animationSpec = tween(SCALE_ANIMATION_DURATION)
                    ) with
                    scaleOut(
                        targetScale = 0.92f,
                        animationSpec = tween(SCALE_ANIMATION_DURATION, delayMillis = 90)
                    )

        }, contentAlignment = Alignment.Center) { target ->
            if (target.isDetail) {
                Timber.d("Load detail $target")
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showOptions = loading.not()
                        },
                    model = target.url,
                    onSuccess = {
                        loading = false
                    },
                    contentDescription = "Original image",
                    placeholder = rememberAsyncImagePainter(model = image.thumbnail)
                )
            } else {
                Timber.d("Load thumbnail $target")
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    model = target.url,
                    contentDescription = "thumbnail image"
                )
            }
        }
        if (loading) CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}


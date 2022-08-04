package com.danteandroi.composewall.ui.detail

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import com.danteandroi.composewall.data.Image
import com.danteandroi.composewall.data.ImageDetailState
import com.danteandroi.composewall.utils.alternativeImageUrl
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
    navigateUp: () -> Unit
) {
    var loading by remember {
        mutableStateOf(true)
    }
    var detailState by remember {
        mutableStateOf(ImageDetailState(false, image.thumbnail))
    }
    Box(
        modifier = modifier
            .clickable {
                detailState = ImageDetailState(false, image.thumbnail)
                navigateUp.invoke()
            }
            .background(Color.Black.copy(alpha = 0.8f))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
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
                    modifier = Modifier.fillMaxWidth(),
                    model = target.url,
                    onSuccess = { loading = false },
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
        if (loading) CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
    }
}
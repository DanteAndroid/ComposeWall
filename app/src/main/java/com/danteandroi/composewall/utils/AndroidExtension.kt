package com.danteandroi.composewall.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @author Dante
 * 2021/12/30
 */
inline fun <reified T> Any?.tryCast(block: T.() -> Unit) {
    if (this is T) {
        block.invoke(this)
    }
}

private val coroutineExceptionHandler: CoroutineExceptionHandler =
    CoroutineExceptionHandler { _, throwable ->
        if (throwable is CancellationException) {
            throw throwable
        } else {
            throwable.printStackTrace()
        }
    }

fun CoroutineScope.safeLaunch(
    exceptionHandler: CoroutineExceptionHandler = coroutineExceptionHandler,
    launchBody: suspend () -> Unit
): Job {
    return this.launch(exceptionHandler) {
        launchBody.invoke()
    }
}

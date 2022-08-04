package com.danteandroi.composewall.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @author Du Wenyu
 * 2021/12/30
 */
inline fun <reified T> Any?.tryCast(block: T.() -> Unit) {
    if (this is T) {
        block.invoke(this)
    }
}

private val coroutineExceptionHandler: CoroutineExceptionHandler =
    CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

fun CoroutineScope.safeLaunch(
    exceptionHandler: CoroutineExceptionHandler = coroutineExceptionHandler,
    launchBody: suspend () -> Unit
): Job {
    return this.launch(exceptionHandler) {
        launchBody.invoke()
    }
}

fun buildKeyValueMap(vararg params: Any): HashMap<String, String> {
    val map = hashMapOf<String, String>()
    for (i in 0 until params.size / 2) {
        val key = params[2 * i]
        val value = params[2 * i + 1]
        map[key.toString()] = value.toString()
    }
    return map
}
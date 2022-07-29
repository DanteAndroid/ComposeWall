package com.danteandroi.composewall.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
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
    CoroutineExceptionHandler { context, throwable ->
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

fun ViewGroup.safeAddView(view: View) {
    if (view.parent != null) {
        (view.parent as? ViewGroup)?.removeAllViews()
    }
    addView(view)
}

fun Drawable?.startAnimatedDrawable(repeat: Boolean = false) {
    if (this is AnimatedVectorDrawable) {
        if (repeat) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                registerAnimationCallback(object : Animatable2.AnimationCallback() {
                    override fun onAnimationEnd(drawable: Drawable?) {
                        start()
                    }
                })
            }
        }
        start()
    }
}

fun Drawable?.stopAnimatedDrawable() {
    if (this is AnimatedVectorDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            clearAnimationCallbacks()
        }
        stop()
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
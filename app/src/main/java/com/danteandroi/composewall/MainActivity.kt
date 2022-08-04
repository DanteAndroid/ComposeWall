package com.danteandroi.composewall

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.view.WindowCompat
import timber.log.Timber

class MainActivity : ComponentActivity() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        Timber.plant(Timber.DebugTree())
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ComposeApp(calculateWindowSizeClass(this))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        context = null
    }

}
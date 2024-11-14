package com.danteandroi.composewall.utils

import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.danteandroi.composewall.R
import timber.log.Timber

/**
 * @author Dante
 * 2022/8/15
 */
object SecretModeUtil {

    private const val SECRET_MODE = "secret_mode"
    private fun setSecretMode(enable: Boolean) = SPUtils.getInstance().put(SECRET_MODE, enable)

    private var lastTime = 0L
    private var toggleTime: Int = 0

    fun isSecretMode() = SPUtils.getInstance().getBoolean(SECRET_MODE)

    fun onClick() {
        if (System.currentTimeMillis() - lastTime < 500) {
            toggleTime++
            Timber.d("onClick: toggle $toggleTime")
        }
        lastTime = System.currentTimeMillis()
        if (toggleTime >= 5) {
            if (isSecretMode()) {
                setSecretMode(false)
                ToastUtils.showShort(R.string.secret_mode_off)
            } else {
                setSecretMode(true)
                ToastUtils.showShort(R.string.secret_mode_on)
            }
            toggleTime = 0
            ThreadUtils.runOnUiThreadDelayed({
                AppUtils.relaunchApp()
            }, 1000)
        }
    }

}
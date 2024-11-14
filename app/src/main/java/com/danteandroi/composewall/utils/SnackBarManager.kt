package com.danteandroi.composewall.utils

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

/**
 * @author Dante
 * 2022/8/3
 */
data class Message(val id: Long, @StringRes val message: Int)

object SnackBarManager {
    private val _messages: MutableStateFlow<List<Message>> = MutableStateFlow(emptyList())
    val messages = _messages.asStateFlow()

    fun showMessages(@StringRes messageTextId: Int) {
        _messages.update { currentMessages ->
            currentMessages + Message(
                id = UUID.randomUUID().mostSignificantBits,
                message = messageTextId
            )
        }
    }

    fun setMessageShown(messageId: Long) {
        _messages.update { currentMessages ->
            currentMessages.filterNot { it.id == messageId }
        }
    }

}
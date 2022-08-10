package com.danteandroi.composewall.utils

import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * @author Du Wenyu
 * 2022/8/9
 */
data class Event(val id: Long, val name: String)

object EventManager {

    private val _eventList: MutableStateFlow<List<Event>> = MutableStateFlow(arrayListOf())
    val eventList = _eventList.asStateFlow()

    fun postEvent(name: String) {
        _eventList.update { currentEvents ->
            currentEvents + Event(UUID.randomUUID().mostSignificantBits, name)
        }
    }

    private fun setEventShown(id: Long) {
        _eventList.update { currentEvents ->
            currentEvents.filterNot { it.id == id }
        }
    }

    @Composable
    fun Handler(handleEvent: suspend (Event) -> Unit) {
        val events by eventList.collectAsState()
        events.lastOrNull()?.let { latestEvent ->
            val scope = rememberCoroutineScope()
            LaunchedEffect(events) {
                scope.launch {
                    handleEvent(latestEvent)
                    setEventShown(latestEvent.id)
                }
            }
        }
    }

}
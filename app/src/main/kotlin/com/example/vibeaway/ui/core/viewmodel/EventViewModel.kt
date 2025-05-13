package com.example.vibeaway.ui.core.viewmodel

import androidx.lifecycle.ViewModel
import com.example.vibeaway.ui.core.viewmodel.model.BasicEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * View model wrapper for ui event driven management.
 * The ui state of the view model that is extending this abstract class should
 * contain an enum (or other preferred model type) that implements interface [BasicEvent].
 * https://developer.android.com/topic/architecture/ui-layer/events
 */
abstract class EventViewModel<T : BasicEvent> : ViewModel() {
    private val _events: MutableStateFlow<ImmutableList<T>> = MutableStateFlow(
        persistentListOf()
    )

    val events = _events.asStateFlow()

    /**
     *  Register another event into the queue.
     */
    protected fun registerEvent(event: T) {
        _events.update { events ->
            val newEvents = events.toMutableList().also { it.add(event) }
            newEvents.toImmutableList()
        }
    }

    /**
     * Removes the first occurrence of the provided event from the queue.
     */
    fun unregisterEvent(event: T) {
        _events.update { events ->
            val newEvents = events.toMutableList().also { it.remove(event) }
            newEvents.toImmutableList()
        }
    }
}

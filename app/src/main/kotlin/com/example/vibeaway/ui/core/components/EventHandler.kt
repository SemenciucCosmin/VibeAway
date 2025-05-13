package com.example.vibeaway.ui.core.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vibeaway.ui.core.viewmodel.model.BasicEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.StateFlow

/**
 * An effect for handling events from view model.
 *
 * @param eventsFlow State flow with list of [BasicEvent] subtype that acts as a queue
 * @param onEvent the action invoked by having at least one event object in the list
 */
@Composable
fun <T : BasicEvent> EventHandler(
    eventsFlow: StateFlow<ImmutableList<T>>,
    onEvent: @Composable (T) -> Unit
) {
    val events by eventsFlow.collectAsStateWithLifecycle()
    val event by remember { derivedStateOf { events.firstOrNull() } }
    event?.let { onEvent(it) }
}

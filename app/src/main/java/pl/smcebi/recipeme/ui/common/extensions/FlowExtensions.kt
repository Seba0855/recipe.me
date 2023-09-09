package pl.smcebi.recipeme.ui.common.extensions

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext

inline fun <T : Any> MutableStateFlow<T>.mutate(block: T.() -> T) {
    value = value.block()
}

/**
 * @see Channel
 */
@Suppress("FunctionName")
fun <T> EventsChannel() = Channel<T>(Channel.BUFFERED)

suspend fun <T> Channel<T>.sendIfActive(value: T) {
    if (coroutineContext.isActive) {
        send(value)
    }
}

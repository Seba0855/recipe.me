package pl.smcebi.recipeme.domain.common

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class CachedFlow<T> private constructor(
    private val internalFlow: MutableSharedFlow<Cached<T>>
) : Flow<T> by internalFlow.toExposedFlow() {

    constructor() : this(
        MutableSharedFlow(
            replay = 1,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    ) {
        internalFlow.tryEmit(Cached.Empty())
    }

    suspend fun emit(value: T) {
        internalFlow.emit(Cached.Data(value))
    }

    suspend fun hasValue(): Boolean =
        internalFlow.first() is Cached.Data

    fun clear() {
        internalFlow.tryEmit(Cached.Empty())
    }

    private sealed class Cached<T> {
        data class Data<T>(val data: T) : Cached<T>()
        class Empty<T> : Cached<T>()
    }

    private companion object {
        private fun <T> Flow<Cached<T>>.toExposedFlow(): Flow<T> =
            filterIsInstance<Cached.Data<T>>()
                .map { it.data }
                .distinctUntilChanged()
    }
}

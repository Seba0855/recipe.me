package pl.smcebi.recipeme.ui.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.smcebi.recipeme.ui.common.extensions.mutate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BottomNavCommunicationBridge @Inject constructor() {

    private val mutableState = MutableStateFlow(true)
    val state: StateFlow<Boolean> = mutableState.asStateFlow()

    fun mutateState(isVisible: Boolean) {
        mutableState.mutate { isVisible }
    }
}
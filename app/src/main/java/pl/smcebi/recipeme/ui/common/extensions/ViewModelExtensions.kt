package pl.smcebi.recipeme.ui.common.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun ViewModel.withProgressBar(
    indicator: (Boolean) -> Unit,
    block: suspend CoroutineScope.() -> Unit,
): Job =
    viewModelScope.launch {
        indicator(true)
        block()
        indicator(false)
    }

package pl.smcebi.recipeme.ui.common.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

fun <T : Any> AppCompatActivity.collectOnLifecycle(flow: Flow<T>, collector: FlowCollector<T>) {
    lifecycleScope.launch {
        flow.collect(collector)
    }
}

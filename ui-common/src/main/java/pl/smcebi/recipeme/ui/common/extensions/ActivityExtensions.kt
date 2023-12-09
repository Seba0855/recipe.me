package pl.smcebi.recipeme.ui.common.extensions

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

fun <T : Any> AppCompatActivity.collectOnLifecycle(flow: Flow<T>, collector: FlowCollector<T>) {
    lifecycleScope.launch {
        flow.collect(collector)
    }
}

fun Activity.setKeyboardVisibilityListener(listener: (isVisible: Boolean) -> Unit) {
    ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, insets ->
        listener(insets.isKeyboardVisible())
        insets
    }
}
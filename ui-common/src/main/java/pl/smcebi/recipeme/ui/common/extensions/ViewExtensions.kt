package pl.smcebi.recipeme.ui.common.extensions

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DimenRes
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnAttach
import androidx.core.view.doOnDetach

inline fun View.afterMeasured(crossinline block: () -> Unit) {
    if (measuredWidth > 0 && measuredHeight > 0) {
        block()
    } else {
        viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (measuredWidth > 0 && measuredHeight > 0) {
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                        block()
                    }
                }
            }
        )
    }
}

inline fun afterMeasured(view: View, vararg views: View, crossinline block: () -> Unit) {
    val allViews = views.asList().plus(view)
    if (allViews.all { it.measuredWidth > 0 && it.measuredHeight > 0 }) {
        block()
    } else {
        view.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (allViews.all { it.measuredWidth > 0 && it.measuredHeight > 0 }) {
                        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        block()
                    }
                }
            }
        )
    }
}

inline fun <T : View> T.setSafeOnClickListener(crossinline onClick: T.() -> Unit) {
    doOnAttach {
        setOnClickListener {
            onClick()
        }
    }
    doOnDetach {
        setOnClickListener(null)
    }
}

inline fun <T : View> T.setSafeOnFocusChangedListener(crossinline onFocus: T.(Boolean) -> Unit) {
    doOnAttach {
        setOnFocusChangeListener { _, hasFocus ->
            onFocus(hasFocus)
        }
    }
    doOnDetach {
        onFocusChangeListener = null
    }
}

fun View.showSoftInput() {
    val imm = context.getSystemService(InputMethodManager::class.java)
    imm.showSoftInput(this, 0)
}

fun View.hideSoftInput() {
    windowToken?.let { token ->
        val imm = context.getSystemService(InputMethodManager::class.java)
        imm.hideSoftInputFromWindow(token, 0)
    }
}

fun View.restartSoftInput() {
    val imm = context.getSystemService(InputMethodManager::class.java)
    imm.restartInput(this)
}

fun View.setPadding(
    left: Int? = null,
    top: Int? = null,
    right: Int? = null,
    bottom: Int? = null
) {
    setPadding(
        left ?: paddingLeft,
        top ?: paddingTop,
        right ?: paddingRight,
        bottom ?: paddingBottom
    )
}

/**
 * Can be used once per parent View
 */
fun View.expandTouchArea(parent: View, @DimenRes paddingRes: Int) {
    doOnAttach {
        val padding = resources.getDimensionPixelSize(paddingRes)
        parent.post {
            val rect = Rect()
            getHitRect(rect)
            rect.inset(-padding, -padding)
            parent.touchDelegate = TouchDelegate(rect, this)
        }
    }
    doOnDetach {
        parent.touchDelegate = null
    }
}

inline fun View.animate(block: ViewPropertyAnimator.() -> Unit) {
    val animator = animate()
    animator.block()
    animator.start()
}

fun View.getRelativePositionTo(view: View): Pair<Int, Int> {
    val firstViewLocation = IntArray(2)
    val secondViewLocation = IntArray(2)

    getLocationInWindow(firstViewLocation)
    view.getLocationInWindow(secondViewLocation)

    val x = firstViewLocation[0] - secondViewLocation[0]
    val y = firstViewLocation[1] - secondViewLocation[1]
    return x to y
}

fun WindowInsetsCompat.isKeyboardVisible(): Boolean = isVisible(WindowInsetsCompat.Type.ime())

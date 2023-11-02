package pl.smcebi.recipeme.ui.common.extensions

import android.util.TypedValue
import android.view.View

context(View)
val Int.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        resources.displayMetrics
    )
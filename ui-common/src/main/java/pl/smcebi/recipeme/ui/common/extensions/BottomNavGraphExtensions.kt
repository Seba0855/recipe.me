package pl.smcebi.recipeme.ui.common.extensions

import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.forEach
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.disableTooltipText() {
    menu.forEach {
        TooltipCompat.setTooltipText(findViewById(it.itemId), null)
    }
}
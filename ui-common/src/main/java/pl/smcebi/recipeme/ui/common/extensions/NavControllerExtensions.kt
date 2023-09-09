package pl.smcebi.recipeme.ui.common.extensions

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.cleanBackStack() {
    currentBackStack.value.firstOrNull()?.let { entry ->
        popBackStack(entry.destination.id, inclusive = true)
    }
}

fun NavController.navigateWithCleanBackStack(deepLink: Uri, navOptions: NavOptions? = null) {
    cleanBackStack()
    navigate(deepLink, navOptions)
}

package pl.smcebi.recipeme.ui.common.extensions

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.StringRes
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.R.id.snackbar_action
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import pl.smcebi.recipeme.ui.common.model.DialogArgs
import pl.smcebi.recipeme.ui.common.model.DialogType
import pl.smcebi.recipeme.uicommon.R

typealias OnClick = () -> Unit

fun <T> Fragment.collectOnViewLifecycle(flow: Flow<T>, collector: FlowCollector<T>) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collector)
        }
    }
}

fun Fragment.onBackPressed(onBackAction: OnClick) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
        onBackAction()
    }
}

fun Fragment.showDialog(
    dialogType: DialogType,
    args: DialogArgs,
    onPositiveButtonClick: OnClick = {},
    onNeutralButtonClick: OnClick = {},
    onNegativeButtonClick: OnClick = {},
    onDismissed: () -> Unit = {}
) = MaterialAlertDialogBuilder(
    requireContext()
).apply {
    setTitle(args.title)
    setMessage(args.message)
    setCancelable(args.cancellable)
    setPositiveButton(args.positiveButtonText) { _, _ ->
        onPositiveButtonClick()
    }
    if (dialogType is DialogType.OptionsDialog && args.neutralButtonText != null) {
        setNeutralButton(args.neutralButtonText) { _, _ ->
            onNeutralButtonClick()
        }
    }
    if (dialogType is DialogType.ConfirmDialog) {
        setNegativeButton(args.negativeButtonText) { _, _ ->
            onNegativeButtonClick()
        }
    }
    setOnDismissListener {
        onDismissed()
    }
    show()
}

val Fragment.activityRootView: View
    get() = requireActivity().findViewById(android.R.id.content)

fun Fragment.showSnackbar(@StringRes message: Int, onDismissed: () -> Unit = {}) {
    showSnackbar(getString(message), onDismissed)
}

fun Fragment.showSnackbar(message: String?, onDismissed: () -> Unit = {}) {
    if (message != null) {
        Snackbar.make(requireContext(), activityRootView, message, Snackbar.LENGTH_LONG)
            .setAction(R.string.common_close_snackbar) { /* NO-OP */ }
            .apply {
                (view as? ViewGroup)
                    ?.getChildAt(0)
                    ?.findViewById<MaterialButton>(snackbar_action)
                    ?.setPadding(size = resources.getDimensionPixelSize(R.dimen.margin_medium))
            }
            .addCallback(
                object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        onDismissed()
                    }
                }
            )
            .show()
    } else {
        showSomethingWentWrong(onDismissed)
    }
}

fun Fragment.showSomethingWentWrong(onDismissed: () -> Unit = {}) {
    showSnackbar(R.string.common_something_went_wrong, onDismissed)
}

fun Fragment.notImplemented(onDismissed: () -> Unit = {}) {
    showSnackbar("Not implemented", onDismissed)
}

fun Fragment.setParentFragmentResultListener(
    requestKey: String,
    listener: (String, Bundle) -> Unit
) {
    parentFragment?.let { parent ->
        parent.parentFragmentManager.setFragmentResultListener(
            requestKey,
            parent.viewLifecycleOwner
        ) { key, bundle ->
            listener(key, bundle)
        }
    }
}

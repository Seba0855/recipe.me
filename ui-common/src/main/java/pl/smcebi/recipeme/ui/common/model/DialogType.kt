package pl.smcebi.recipeme.ui.common.model

sealed class DialogType {
    object InfoDialog : DialogType()
    object OptionsDialog : DialogType()
    object ConfirmDialog : DialogType()
}

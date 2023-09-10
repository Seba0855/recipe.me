package pl.smcebi.recipeme.ui.common.model

data class DialogArgs(
    val title: String? = null,
    val message: String? = null,
    val cancellable: Boolean = true,
    val positiveButtonText: String,
    val neutralButtonText: String? = null,
    val negativeButtonText: String? = null
)

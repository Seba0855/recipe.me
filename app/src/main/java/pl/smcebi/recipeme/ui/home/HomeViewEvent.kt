package pl.smcebi.recipeme.ui.home

internal sealed class HomeViewEvent {
    data class ShowError(val message: String?) : HomeViewEvent()
}

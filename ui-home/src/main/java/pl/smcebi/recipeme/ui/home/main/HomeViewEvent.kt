package pl.smcebi.recipeme.ui.home.main

internal sealed class HomeViewEvent {
    data class ShowError(val message: String?) : HomeViewEvent()
}

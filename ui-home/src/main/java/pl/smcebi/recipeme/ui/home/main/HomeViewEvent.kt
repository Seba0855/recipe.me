package pl.smcebi.recipeme.ui.home.main

internal sealed interface HomeViewEvent {
    @JvmInline
    value class NavigateDetails(val recipeId: String) : HomeViewEvent

    @JvmInline
    value class ShowError(val message: String?) : HomeViewEvent
}

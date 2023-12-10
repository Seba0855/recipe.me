package pl.smcebi.recipeme.ui.saved

internal sealed interface SavedRecipesEvent {
    data object ShowMessage : SavedRecipesEvent
}
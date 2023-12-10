package pl.smcebi.recipeme.ui.saved

internal sealed interface SavedRecipesEvent {
    @JvmInline
    value class NavigateDetails(val recipeId: String) : SavedRecipesEvent
    data object ShowRecipeRemovedMessage : SavedRecipesEvent
}
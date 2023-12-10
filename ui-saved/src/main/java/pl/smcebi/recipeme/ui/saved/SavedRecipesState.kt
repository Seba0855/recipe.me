package pl.smcebi.recipeme.ui.saved

import pl.smcebi.recipeme.domain.recipes.model.RecipesUI

internal data class SavedRecipesState(
    val recipes: List<RecipesUI> = emptyList()
)

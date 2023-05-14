package pl.smcebi.recipeme.ui.home

import pl.smcebi.recipeme.domain.recipes.model.RecipesUI

internal data class HomeViewState(
    val recipes: List<RecipesUI> = emptyList()
)

package pl.smcebi.recipeme.ui.home

import pl.smcebi.recipeme.domain.recipes.model.RecipesUI

internal data class HomeViewState(
    val inProgress: Boolean = false,
    val isError: Boolean = false,
    val recipes: List<RecipesUI> = emptyList()
)

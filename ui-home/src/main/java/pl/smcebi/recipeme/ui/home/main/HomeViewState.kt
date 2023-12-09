package pl.smcebi.recipeme.ui.home.main

import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import pl.smcebi.recipeme.domain.recipes.model.SuggestionUI

internal data class HomeViewState(
    val inProgress: Boolean = false,
    val searchInProgress: Boolean = false,
    val isError: Boolean = false,
    val showInitialMessage: Boolean = true,
    val noSearchResult: Boolean = false,
    val recipes: List<RecipesUI> = emptyList(),
    val searchSuggestions: List<SuggestionUI> = emptyList(),
    val searchFailed: Boolean = false,
)

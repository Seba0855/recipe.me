package pl.smcebi.recipeme.ui.home.main

import pl.smcebi.recipeme.domain.common.utils.Selectable
import pl.smcebi.recipeme.domain.common.utils.Selectable.Companion.toSelectable
import pl.smcebi.recipeme.domain.recipes.model.MealType
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
    val mealTypeEntries: List<Selectable<MealType>> = MealType.entries.toSelectable(0)
)

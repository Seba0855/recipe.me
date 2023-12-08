package pl.smcebi.recipeme.ui.home.details

import pl.smcebi.recipeme.domain.recipes.model.RecipesUI

internal data class RecipeDetailsState(
    val inProgress: Boolean = false,
    val isError: Boolean = false,
    val recipe: RecipesUI? = null,
    val calories: String = "",
    val carbs: String = "",
    val fat: String = "",
    val protein: String = "",
    val nutritionInProgress: Boolean = false,
)

package pl.smcebi.recipeme.ui.home.details

internal data class RecipeDetailsState(
    val calories: String = "",
    val carbs: String = "",
    val fat: String = "",
    val protein: String = "",
    val nutritionInProgress: Boolean = false,
)

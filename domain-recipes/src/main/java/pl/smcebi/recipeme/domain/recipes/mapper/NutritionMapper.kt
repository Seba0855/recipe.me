package pl.smcebi.recipeme.domain.recipes.mapper

import pl.smcebi.recipeme.domain.recipes.model.NutritionUI
import pl.smcebi.recipeme.recipes.NutritionResponse

internal fun NutritionResponse.mapUI(): NutritionUI = NutritionUI(
    calories = buildString { append("\uD83D\uDD25 ", calories, " kcal") },
    carbs = buildString { append("\uD83E\uDED9 ", carbs) },
    fat = buildString { append("\uD83C\uDF56 ", fat) },
    protein = buildString { append("\uD83C\uDF57 ", protein) },
)
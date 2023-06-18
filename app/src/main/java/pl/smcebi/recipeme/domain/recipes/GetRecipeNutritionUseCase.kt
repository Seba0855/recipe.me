package pl.smcebi.recipeme.domain.recipes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.di.annotations.DispatcherIO
import pl.smcebi.recipeme.domain.common.DomainResult
import pl.smcebi.recipeme.domain.common.getErrorMessage
import pl.smcebi.recipeme.domain.recipes.model.NutritionUI
import pl.smcebi.recipeme.infrastructure.model.recipes.NutritionResponse
import pl.smcebi.recipeme.infrastructure.remote.datasource.recipes.RecipesDataSource
import javax.inject.Inject

class GetRecipeNutritionUseCase @Inject internal constructor(
    private val dataSource: RecipesDataSource,
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(recipeId: String): DomainResult<NutritionUI, String?> =
        withContext(dispatcher) {
            dataSource.getRecipeNutrition(recipeId).map(
                onSuccess = { response ->
                    DomainResult.Success(response.mapUI())
                },
                onError = { _, errorBody ->
                    DomainResult.Failure(errorBody.getErrorMessage())
                }
            )
        }

    private fun NutritionResponse.mapUI(): NutritionUI = NutritionUI(
        calories = buildString { append("\uD83D\uDD25 ", calories, " kcal") },
        carbs = buildString { append("\uD83E\uDED9 ", carbs) },
        fat = buildString { append("\uD83C\uDF56 ", fat) },
        protein = buildString { append("\uD83C\uDF57 ", protein) },
    )
}
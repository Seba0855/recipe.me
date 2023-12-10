package pl.smcebi.recipeme.domain.recipes.details

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.domain.common.dispatchers.DispatcherIO
import pl.smcebi.recipeme.domain.common.utils.DomainResult
import pl.smcebi.recipeme.domain.common.utils.getErrorMessage
import pl.smcebi.recipeme.domain.recipes.mapper.mapUI
import pl.smcebi.recipeme.domain.recipes.model.NutritionUI
import pl.smcebi.recipeme.recipes.RecipesDataSource
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
}

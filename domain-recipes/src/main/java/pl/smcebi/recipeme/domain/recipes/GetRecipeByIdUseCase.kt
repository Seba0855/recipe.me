package pl.smcebi.recipeme.domain.recipes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.domain.common.dispatchers.DispatcherIO
import pl.smcebi.recipeme.domain.common.utils.DomainResult
import pl.smcebi.recipeme.domain.common.utils.getErrorMessage
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import pl.smcebi.recipeme.recipes.RecipesDataSource
import javax.inject.Inject

class GetRecipeByIdUseCase @Inject internal constructor(
    private val recipesDataSource: RecipesDataSource,
    private val recipesMapper: RecipesMapper,
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(
        recipeId: String,
        includeNutrition: Boolean = false
    ): DomainResult<RecipesUI, String?> = withContext(dispatcher) {
        recipesDataSource.getRecipeById(recipeId, includeNutrition).map(
            onSuccess = { response ->
                DomainResult.Success(recipesMapper.map(response))
            },
            onError = { _, errorBody ->
                DomainResult.Failure(errorBody.getErrorMessage())
            }
        )
    }
}
package pl.smcebi.recipeme.domain.recipes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.datasource.common.Mock
import pl.smcebi.recipeme.domain.common.dispatchers.DispatcherIO
import pl.smcebi.recipeme.domain.common.utils.DomainResult
import pl.smcebi.recipeme.domain.common.utils.getErrorMessage
import pl.smcebi.recipeme.domain.recipes.mapper.RecipesMapper
import pl.smcebi.recipeme.domain.recipes.model.MealType
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import pl.smcebi.recipeme.recipes.RecipesDataSource
import javax.inject.Inject

class GetRandomRecipesUseCase @Inject internal constructor(
    private val dataSource: RecipesDataSource,
    private val recipesMapper: RecipesMapper,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(mealType: MealType = MealType.RANDOM): DomainResult<List<RecipesUI>, String?> =
        withContext(dispatcher) {
            dataSource.getRandomRecipes(
                limitLicense = true,
                tags = mealType.externalName.orEmpty(),
                number = VISIBLE_ITEMS
            ).map(
                onSuccess = { response ->
                    DomainResult.Success(response.recipes.map(recipesMapper::map))
                },
                onError = { _, errorBody ->
                    DomainResult.Failure(errorBody.getErrorMessage())
                }
            )
        }

    private companion object {
        const val VISIBLE_ITEMS = 10
    }
}

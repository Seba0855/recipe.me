package pl.smcebi.recipeme.domain.recipes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.di.DispatcherIO
import pl.smcebi.recipeme.domain.common.DomainResult
import pl.smcebi.recipeme.domain.common.getErrorMessage
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import pl.smcebi.recipeme.infrastructure.model.recipes.RecipeResponse
import pl.smcebi.recipeme.infrastructure.remote.datasource.recipes.RecipesDataSource
import javax.inject.Inject

class GetRandomRecipesUseCase @Inject internal constructor(
    private val dataSource: RecipesDataSource,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(tags: String): DomainResult<List<RecipesUI>, String?> =
        withContext(dispatcher) {
            dataSource.getRandomRecipes(
                limitLicense = true,
                tags = tags,
                number = VISIBLE_ITEMS
            ).map(
                onSuccess = { response ->
                    DomainResult.Success(response.mapUI())
                },
                onError = { _, errorBody ->
                    DomainResult.Failure(errorBody.getErrorMessage())
                }
            )
        }

    private fun List<RecipeResponse>.mapUI(): List<RecipesUI> =
        this.map { recipeResponse ->
            RecipesUI(
                title = recipeResponse.title
            )
        }

    private companion object {
        const val VISIBLE_ITEMS = 10
    }
}

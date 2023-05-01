package pl.smcebi.recipeme.domain.recipes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.di.DispatcherIO
import pl.smcebi.recipeme.domain.common.DomainResult
import pl.smcebi.recipeme.domain.common.getErrorMessage
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import pl.smcebi.recipeme.infrastructure.model.recipes.RecipeListResponse
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

    private fun RecipeListResponse.mapUI(): List<RecipesUI> =
        this.recipes.map { recipeResponse ->
            RecipesUI(
                id = recipeResponse.id,
                title = recipeResponse.title,
                image = recipeResponse.image
            )
        }

    private companion object {
        const val VISIBLE_ITEMS = 10
    }
}

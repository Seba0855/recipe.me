package pl.smcebi.recipeme.domain.recipes

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.di.annotations.DispatcherIO
import pl.smcebi.recipeme.domain.common.DomainResult
import pl.smcebi.recipeme.domain.common.getErrorMessage
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import pl.smcebi.recipeme.infrastructure.remote.datasource.recipes.RecipesDataSource

class GetRandomRecipesUseCase @Inject internal constructor(
    private val dataSource: RecipesDataSource,
    private val recipesMapper: RecipesMapper,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(tags: String? = ""): DomainResult<List<RecipesUI>, String?> =
        withContext(dispatcher) {
            dataSource.getRandomRecipes(
                limitLicense = true,
                tags = tags,
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

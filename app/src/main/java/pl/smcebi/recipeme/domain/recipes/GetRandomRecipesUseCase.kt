package pl.smcebi.recipeme.domain.recipes

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.di.DispatcherIO
import pl.smcebi.recipeme.domain.common.DomainResult
import pl.smcebi.recipeme.domain.common.getErrorMessage
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import pl.smcebi.recipeme.infrastructure.remote.datasource.recipes.RecipesDataSource

class GetRandomRecipesUseCase @Inject internal constructor(
    private val dataSource: RecipesDataSource,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(tags: String): DomainResult<RecipesUI, String?> =
        withContext(dispatcher) {
            dataSource.getRandomRecipes(
                limitLicense = true,
                tags = tags,
                number = VISIBLE_ITEMS
            ).map(
                onSuccess = { response ->
                    val recipeUI = RecipesUI(
                        title = response.title
                    )
                    DomainResult.Success(recipeUI)
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

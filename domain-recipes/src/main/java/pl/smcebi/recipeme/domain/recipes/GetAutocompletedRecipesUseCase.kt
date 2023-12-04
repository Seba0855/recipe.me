package pl.smcebi.recipeme.domain.recipes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.datasource.common.Mock
import pl.smcebi.recipeme.domain.common.dispatchers.DispatcherIO
import pl.smcebi.recipeme.domain.common.images.ImageMapper
import pl.smcebi.recipeme.domain.common.utils.DomainResult
import pl.smcebi.recipeme.domain.common.utils.getErrorMessage
import pl.smcebi.recipeme.domain.recipes.model.SuggestionUI
import pl.smcebi.recipeme.recipes.AutocompleteResponse
import pl.smcebi.recipeme.recipes.RecipesDataSource
import javax.inject.Inject

class GetAutocompletedRecipesUseCase @Inject internal constructor(
    private val imageMapper: ImageMapper,
    private val recipesDataSource: RecipesDataSource,
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(query: String): DomainResult<List<SuggestionUI>, String?> =
        withContext(dispatcher) {
            recipesDataSource.autocompleteRecipeSearch(query, number = 10).map(
                onSuccess = { autocompleteResponses ->
                    DomainResult.Success(autocompleteResponses.toSuggestionUI())
                },
                onError = { _, errorBody ->
                    DomainResult.Failure(errorBody.getErrorMessage())
                }
            )
        }

    private fun List<AutocompleteResponse>.toSuggestionUI(): List<SuggestionUI> =
        map { response ->
            SuggestionUI(
                id = response.id.toString(),
                title = response.title.replaceFirstChar { it.uppercaseChar() },
                imageUrl = imageMapper.mapRecipes(
                    recipeID = response.id.toString(),
                    imageSize = ImageMapper.ImageSize.SMALL,
                    imageType = response.imageType
                )
            )
        }
}
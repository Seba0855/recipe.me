package pl.smcebi.recipeme.infrastructure.remote.datasource.recipes

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import pl.smcebi.recipeme.infrastructure.model.recipes.RecipeResponse
import pl.smcebi.recipeme.infrastructure.remote.api.recipes.RecipesApi
import pl.smcebi.recipeme.di.NetworkCoroutineDispatcher
import pl.smcebi.recipeme.infrastructure.model.recipes.QuickAnswerResponse
import pl.smcebi.recipeme.infrastructure.remote.common.NetworkResult
import pl.smcebi.recipeme.infrastructure.remote.common.apiCall

internal class RecipesNetworkDataSource @Inject constructor(
    private val api: RecipesApi,
    @NetworkCoroutineDispatcher private val dispatcher: CoroutineDispatcher,
    private val json: Json
) : RecipesDataSource {

    override suspend fun getRandomRecipes(
        limitLicense: Boolean,
        tags: String,
        number: Int
    ): NetworkResult<List<RecipeResponse>> {
        return apiCall(dispatcher, json) {
            api.getRandomRecipes(
                limitLicense = true,
                tags = tags,
                number = number
            )
        }
    }

    override suspend fun getQuickAnswer(query: String): NetworkResult<QuickAnswerResponse> =
        apiCall(dispatcher, json) {
            api.getQuickAnswer(query)
        }
}

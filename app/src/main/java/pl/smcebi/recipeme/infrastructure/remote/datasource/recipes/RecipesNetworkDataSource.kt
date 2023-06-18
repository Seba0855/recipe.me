package pl.smcebi.recipeme.infrastructure.remote.datasource.recipes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import pl.smcebi.recipeme.di.annotations.NetworkCoroutineDispatcher
import pl.smcebi.recipeme.infrastructure.model.recipes.NutritionResponse
import pl.smcebi.recipeme.infrastructure.model.recipes.QuickAnswerResponse
import pl.smcebi.recipeme.infrastructure.model.recipes.RecipeListResponse
import pl.smcebi.recipeme.infrastructure.remote.api.recipes.RecipesApi
import pl.smcebi.recipeme.infrastructure.remote.common.NetworkResult
import pl.smcebi.recipeme.infrastructure.remote.common.apiCall
import javax.inject.Inject

internal class RecipesNetworkDataSource @Inject constructor(
    private val api: RecipesApi,
    @NetworkCoroutineDispatcher private val dispatcher: CoroutineDispatcher,
    private val json: Json
) : RecipesDataSource {

    override suspend fun getRandomRecipes(
        limitLicense: Boolean,
        tags: String?,
        number: Int
    ): NetworkResult<RecipeListResponse> =
        apiCall(dispatcher, json) {
            api.getRandomRecipes(
                limitLicense = true,
                tags = tags,
                number = number
            )
        }

    override suspend fun getQuickAnswer(query: String): NetworkResult<QuickAnswerResponse> =
        apiCall(dispatcher, json) {
            api.getQuickAnswer(query)
        }

    override suspend fun getRecipeNutrition(recipeId: String): NetworkResult<NutritionResponse> =
        apiCall(dispatcher, json) {
            api.getRecipeNutrition(recipeId)
        }
}

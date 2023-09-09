package pl.smcebi.recipeme.recipes.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import pl.smcebi.recipeme.datasource.common.NetworkCoroutineDispatcher
import pl.smcebi.recipeme.datasource.common.NetworkResult
import pl.smcebi.recipeme.datasource.common.apiCall
import pl.smcebi.recipeme.recipes.NutritionResponse
import pl.smcebi.recipeme.recipes.QuickAnswerResponse
import pl.smcebi.recipeme.recipes.RecipesDataSource
import pl.smcebi.recipeme.retrofit.recipes.RecipesApi
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
    ): NetworkResult<pl.smcebi.recipeme.recipes.RecipeListResponse> =
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

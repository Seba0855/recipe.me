package pl.smcebi.recipeme.recipes

import pl.smcebi.recipeme.datasource.common.NetworkResult

interface RecipesDataSource {

    suspend fun getRandomRecipes(
        limitLicense: Boolean,
        tags: String?,
        number: Int
    ): NetworkResult<RecipeListResponse>

    suspend fun getQuickAnswer(query: String): NetworkResult<QuickAnswerResponse>

    suspend fun getRecipeNutrition(recipeId: String): NetworkResult<NutritionResponse>

    suspend fun autocompleteRecipeSearch(query: String, number: Int): NetworkResult<List<AutocompleteResponse>>
}

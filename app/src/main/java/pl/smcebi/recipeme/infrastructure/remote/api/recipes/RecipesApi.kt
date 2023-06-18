package pl.smcebi.recipeme.infrastructure.remote.api.recipes

import pl.smcebi.recipeme.infrastructure.model.recipes.NutritionResponse
import pl.smcebi.recipeme.infrastructure.model.recipes.QuickAnswerResponse
import pl.smcebi.recipeme.infrastructure.model.recipes.RecipeListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface RecipesApi {

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("limitLicense") limitLicense: Boolean,
        @Query("tags") tags: String?,
        @Query("number") number: Int
    ): RecipeListResponse

    @GET("recipes/quickAnswer")
    suspend fun getQuickAnswer(@Query("q") query: String): QuickAnswerResponse

    @GET("recipes/{id}/nutritionWidget.json")
    suspend fun getRecipeNutrition(@Path("id") recipeId: String): NutritionResponse
}

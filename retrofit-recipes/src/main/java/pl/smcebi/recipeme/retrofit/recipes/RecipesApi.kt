package pl.smcebi.recipeme.retrofit.recipes

import pl.smcebi.recipeme.recipes.AutocompleteResponse
import pl.smcebi.recipeme.recipes.NutritionResponse
import pl.smcebi.recipeme.recipes.QuickAnswerResponse
import pl.smcebi.recipeme.recipes.RecipeListResponse
import pl.smcebi.recipeme.recipes.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesApi {

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("limitLicense") limitLicense: Boolean,
        @Query("tags") tags: String?,
        @Query("number") number: Int
    ): RecipeListResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeById(
        @Path("id") recipeId: String,
        @Query("includeNutrition") includeNutrition: Boolean,
    ): RecipeResponse

    @GET("recipes/quickAnswer")
    suspend fun getQuickAnswer(@Query("q") query: String): QuickAnswerResponse

    @GET("recipes/{id}/nutritionWidget.json")
    suspend fun getRecipeNutrition(@Path("id") recipeId: String): NutritionResponse

    @GET("recipes/autocomplete")
    suspend fun autocompleteRecipes(
        @Query("query") query: String,
        @Query("number") number: Int
    ): List<AutocompleteResponse>
}

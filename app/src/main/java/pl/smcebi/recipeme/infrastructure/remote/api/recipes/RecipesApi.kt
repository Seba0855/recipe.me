package pl.smcebi.recipeme.infrastructure.remote.api.recipes

import pl.smcebi.recipeme.infrastructure.model.recipes.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RecipesApi {

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("limitLicense") limitLicense: Boolean,
        @Query("tags") tags: String,
        @Query("number") number: Int
    ): List<RecipeResponse>
}

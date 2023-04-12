package pl.smcebi.recipeme.infrastructure.remote.datasource.recipes

import pl.smcebi.recipeme.infrastructure.model.recipes.RecipeResponse
import pl.smcebi.recipeme.infrastructure.remote.common.NetworkResult

internal interface RecipesDataSource {

    suspend fun getRandomRecipes(
        limitLicense: Boolean,
        tags: String,
        number: Int
    ): NetworkResult<RecipeResponse>
}

package pl.smcebi.recipeme.infrastructure.remote.datasource.recipes

import pl.smcebi.recipeme.infrastructure.model.recipes.NutritionResponse
import javax.inject.Inject
import pl.smcebi.recipeme.infrastructure.model.recipes.QuickAnswerResponse
import pl.smcebi.recipeme.infrastructure.model.recipes.RecipeListResponse
import pl.smcebi.recipeme.infrastructure.model.recipes.RecipeResponse
import pl.smcebi.recipeme.infrastructure.remote.common.NetworkResult

internal class MockRecipesDataSource @Inject constructor() : RecipesDataSource {
    override suspend fun getRandomRecipes(
        limitLicense: Boolean,
        tags: String?,
        number: Int
    ): NetworkResult<RecipeListResponse> = NetworkResult.Success(
        RecipeListResponse(
            listOf(
                RecipeResponse(
                    id = 3369,
                    title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
                    image = "https://spoonacular.com/recipeImages/716429-556x370.jpg",
                    imageType = "eget",
                    servings = 5431,
                    readyInMinutes = 3615,
                    license = null,
                    sourceName = "Terrance Allen",
                    sourceUrl = "https://www.google.com/#q=posuere",
                    spoonacularSourceUrl = "https://search.yahoo.com/search?p=propriae",
                    aggregateLikes = 2960,
                    healthScore = 4.5,
                    spoonacularScore = null,
                    pricePerServing = 6.7,
                    cheap = false,
                    creditsText = "quam",
                    dairyFree = false,
                    gaps = "consectetuer",
                    glutenFree = false,
                    instructions = "dictumst",
                    ketogenic = null,
                    lowFodmap = false,
                    sustainable = false,
                    vegan = false,
                    vegetarian = false,
                    veryHealthy = false,
                    veryPopular = false,
                    whole30 = null,
                    dishTypes = listOf(),
                    extendedIngredients = listOf(),
                    summary = "definitiones"
                ),
                RecipeResponse(
                    id = 8681,
                    title = "Summery Tomato Soup with Pasta and Chickpeas",
                    image = "https://spoonacular.com/recipeImages/794351-556x370.jpg",
                    imageType = "similique",
                    servings = 9259,
                    readyInMinutes = 2867,
                    license = null,
                    sourceName = "Nicole O'Neil",
                    sourceUrl = "https://www.google.com/#q=perpetua",
                    spoonacularSourceUrl = "http://www.bing.com/search?q=morbi",
                    aggregateLikes = 9577,
                    healthScore = 8.9,
                    spoonacularScore = null,
                    pricePerServing = 10.11,
                    cheap = false,
                    creditsText = "fermentum",
                    dairyFree = false,
                    gaps = "errem",
                    glutenFree = false,
                    instructions = "vehicula",
                    ketogenic = null,
                    lowFodmap = false,
                    sustainable = false,
                    vegan = false,
                    vegetarian = false,
                    veryHealthy = false,
                    veryPopular = false,
                    whole30 = null,
                    dishTypes = listOf(),
                    extendedIngredients = listOf(),
                    summary = "perpetua"
                ),
                RecipeResponse(
                    id = 6602,
                    title = "Creamy tomato soup",
                    image = "https://spoonacular.com/recipeImages/640713-556x370.jpg",
                    imageType = "comprehensam",
                    servings = 1151,
                    readyInMinutes = 1255,
                    license = null,
                    sourceName = "Romeo Wood",
                    sourceUrl = "https://duckduckgo.com/?q=penatibus",
                    spoonacularSourceUrl = "https://search.yahoo.com/search?p=dicta",
                    aggregateLikes = 7017,
                    healthScore = 12.13,
                    spoonacularScore = null,
                    pricePerServing = 14.15,
                    cheap = false,
                    creditsText = "aptent",
                    dairyFree = false,
                    gaps = "mauris",
                    glutenFree = false,
                    instructions = "cetero",
                    ketogenic = null,
                    lowFodmap = false,
                    sustainable = false,
                    vegan = false,
                    vegetarian = false,
                    veryHealthy = false,
                    veryPopular = false,
                    whole30 = null,
                    dishTypes = listOf(),
                    extendedIngredients = listOf(),
                    summary = "recteque"
                ),
                RecipeResponse(
                    id = 9760,
                    title = "Creamy tomato ",
                    image = "https://spoonacular.com/recipeImages/640713-556x370.jpg",
                    imageType = "justo",
                    servings = 9622,
                    readyInMinutes = 3302,
                    license = null,
                    sourceName = "Adrienne Walls",
                    sourceUrl = "https://duckduckgo.com/?q=ea",
                    spoonacularSourceUrl = "https://www.google.com/#q=dignissim",
                    aggregateLikes = 9811,
                    healthScore = 16.17,
                    spoonacularScore = null,
                    pricePerServing = 18.19,
                    cheap = false,
                    creditsText = "molestie",
                    dairyFree = false,
                    gaps = "doctus",
                    glutenFree = false,
                    instructions = "eruditi",
                    ketogenic = null,
                    lowFodmap = false,
                    sustainable = false,
                    vegan = false,
                    vegetarian = false,
                    veryHealthy = false,
                    veryPopular = false,
                    whole30 = null,
                    dishTypes = listOf(),
                    extendedIngredients = listOf(),
                    summary = "luptatum"
                )
            )
        )
    )

    override suspend fun getQuickAnswer(query: String): NetworkResult<QuickAnswerResponse> =
        NetworkResult.Success(
            QuickAnswerResponse(answer = "dummy answer", image = "dummy link")
        )

    override suspend fun getRecipeNutrition(recipeId: String): NetworkResult<NutritionResponse> =
        NetworkResult.Success(
            NutritionResponse(calories = "384", carbs = "12g", fat = "14g", protein = "16g")
        )
}

package pl.smcebi.recipeme.domain.recipes.mapper

import dagger.Reusable
import pl.smcebi.recipeme.database.entity.RecipeEntity
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import javax.inject.Inject

@Reusable
internal class RecipeToEntityMapper @Inject internal constructor() {

    fun mapToEntity(recipesUI: RecipesUI): RecipeEntity =
        RecipeEntity(
            recipeId = recipesUI.id,
            title = recipesUI.title,
            imageUrl = recipesUI.imageUrl,
            durationAndServings = recipesUI.durationAndServings,
            description = recipesUI.description,
            readyInMinutes = recipesUI.readyInMinutes,
            servings = recipesUI.servings,
            dishType = recipesUI.dishType.orEmpty()
        )

    fun mapReverse(recipeEntity: RecipeEntity): RecipesUI =
        RecipesUI(
            id = recipeEntity.recipeId,
            title = recipeEntity.title,
            description = recipeEntity.description,
            imageUrl = recipeEntity.imageUrl,
            readyInMinutes = recipeEntity.readyInMinutes,
            servings = recipeEntity.servings,
            durationAndServings = recipeEntity.durationAndServings,
            dishType = recipeEntity.dishType,
            ingredientsList = listOf(),
            instructions = listOf()
        )
}
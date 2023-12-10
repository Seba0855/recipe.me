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
            imageUrl = recipesUI.imageUrl,
            durationAndServings = recipesUI.durationAndServings
        )
}
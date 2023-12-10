package pl.smcebi.recipeme.domain.recipes.mapper

import dagger.Reusable
import pl.smcebi.recipeme.domain.common.images.ImageMapper
import pl.smcebi.recipeme.domain.recipes.model.IngredientUI
import pl.smcebi.recipeme.domain.recipes.model.InstructionUI
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import pl.smcebi.recipeme.recipes.ExtendedIngredientResponse
import pl.smcebi.recipeme.recipes.InstructionStepResponse
import pl.smcebi.recipeme.recipes.RecipeResponse
import javax.inject.Inject

@Reusable
internal class RecipesMapper @Inject internal constructor(
    private val imageMapper: ImageMapper,
) {

    fun map(recipeResponse: RecipeResponse): RecipesUI =
        RecipesUI(
            id = recipeResponse.id.toString(),
            title = recipeResponse.title,
            imageUrl = recipeResponse.image.orEmpty(),
            readyInMinutes = recipeResponse.readyInMinutes,
            servings = recipeResponse.servings,
            durationAndServings = buildString {
                append(
                    recipeResponse.readyInMinutes,
                    " Minutes | ",
                    recipeResponse.servings,
                    " Servings"
                )
            },
            dishType = recipeResponse.dishTypes?.firstOrNull()?.replaceFirstChar(Char::uppercase),
            description = recipeResponse.summary,
            ingredientsList = recipeResponse.extendedIngredients?.map(::mapIngredients).orEmpty(),
            instructions = recipeResponse.analyzedInstructions
                .firstOrNull()?.steps?.toInstructionUI().orEmpty()
        )

    private fun mapIngredients(ingredientResponse: ExtendedIngredientResponse): IngredientUI =
        IngredientUI(
            id = ingredientResponse.id,
            name = ingredientResponse.name.replaceFirstChar(Char::uppercase),
            imageUrl = ingredientResponse.image?.let(imageMapper::mapIngredients),
            amount = ingredientResponse.measures.metric.amount,
            unit = ingredientResponse.measures.metric.unitShort,
        )


    private fun List<InstructionStepResponse>.toInstructionUI(): List<InstructionUI> =
        map { instruction ->
            InstructionUI(
                number = instruction.number.toString(),
                instruction = instruction.step,
            )
        }
}

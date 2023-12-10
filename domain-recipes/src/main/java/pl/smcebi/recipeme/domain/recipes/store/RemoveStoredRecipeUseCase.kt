package pl.smcebi.recipeme.domain.recipes.store

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.database.dao.RecipeDao
import pl.smcebi.recipeme.database.entity.RecipeEntity
import pl.smcebi.recipeme.domain.common.dispatchers.DispatcherIO
import pl.smcebi.recipeme.domain.common.utils.DomainResult
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import javax.inject.Inject

class RemoveStoredRecipeUseCase @Inject internal constructor(
    private val recipeDao: RecipeDao,
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(recipesUI: RecipesUI): DomainResult<Unit, Unit> = withContext(dispatcher) {
        recipeDao.deleteRecipe(
            RecipeEntity(
                recipeId = recipesUI.id,
                title = recipesUI.title,
                imageUrl = recipesUI.imageUrl,
                durationAndServings = recipesUI.durationAndServings,
            )
        )
        DomainResult.success()
    }
}
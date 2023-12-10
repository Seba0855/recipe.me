package pl.smcebi.recipeme.domain.recipes.store

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import pl.smcebi.recipeme.database.dao.RecipeDao
import pl.smcebi.recipeme.domain.common.dispatchers.DispatcherIO
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import javax.inject.Inject

class CollectStoredRecipesUseCase @Inject internal constructor(
    private val recipeDao: RecipeDao,
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
){
    // TODO: this should be refactored
    operator fun invoke(): Flow<List<RecipesUI>> = recipeDao.getRecipesStream().map { list ->
        list.map { entity ->
            RecipesUI(
                id = entity.recipeId,
                title = entity.title,
                description = "",
                imageUrl = entity.imageUrl,
                readyInMinutes = 0,
                servings = 0,
                durationAndServings = entity.durationAndServings,
                dishType = null,
                ingredientsList = listOf(),
                instructions = listOf()
            )
        }
    }.flowOn(dispatcher)
}
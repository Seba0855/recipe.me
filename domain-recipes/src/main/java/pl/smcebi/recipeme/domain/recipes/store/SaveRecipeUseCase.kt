package pl.smcebi.recipeme.domain.recipes.store

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.database.dao.RecipeDao
import pl.smcebi.recipeme.domain.common.dispatchers.DispatcherIO
import pl.smcebi.recipeme.domain.common.utils.DomainResult
import pl.smcebi.recipeme.domain.recipes.mapper.RecipeToEntityMapper
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import javax.inject.Inject

class SaveRecipeUseCase @Inject internal constructor(
    private val recipeDao: RecipeDao,
    private val recipeToEntityMapper: RecipeToEntityMapper,
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(recipesUI: RecipesUI): DomainResult<Unit, Unit> =
        withContext(dispatcher) {
            recipeDao.insertRecipe(recipeToEntityMapper.mapToEntity(recipesUI))
            DomainResult.success()
        }
}
package pl.smcebi.recipeme.domain.recipes.store

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import pl.smcebi.recipeme.database.dao.RecipeDao
import pl.smcebi.recipeme.domain.common.dispatchers.DispatcherIO
import pl.smcebi.recipeme.domain.recipes.mapper.RecipeToEntityMapper
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import javax.inject.Inject

class CollectStoredRecipesUseCase @Inject internal constructor(
    private val recipeDao: RecipeDao,
    private val recipeToEntityMapper: RecipeToEntityMapper,
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
) {
    operator fun invoke(): Flow<List<RecipesUI>> = recipeDao.getRecipesStream().map { recipeList ->
        recipeList.map(recipeToEntityMapper::mapReverse)
    }.flowOn(dispatcher)
}
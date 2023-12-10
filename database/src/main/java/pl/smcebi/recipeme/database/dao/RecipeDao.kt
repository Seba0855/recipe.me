package pl.smcebi.recipeme.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import pl.smcebi.recipeme.database.DatabaseConstants.TABLE_RECIPE
import pl.smcebi.recipeme.database.entity.RecipeEntity

@Dao
interface RecipeDao {

    @Query("SELECT * FROM $TABLE_RECIPE")
    suspend fun getRecipesStream(): Flow<List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Transaction
    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)
}

package pl.smcebi.recipeme.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import pl.smcebi.recipeme.database.entity.RecipeEntity

@Dao
internal interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Transaction
    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)
}

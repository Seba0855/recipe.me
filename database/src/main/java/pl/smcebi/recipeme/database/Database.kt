package pl.smcebi.recipeme.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.smcebi.recipeme.database.dao.RecipeDao
import pl.smcebi.recipeme.database.entity.RecipeEntity

@Database(
    version = 1,
    entities = [RecipeEntity::class]
)
internal abstract class Database : RoomDatabase() {
    abstract val recipeDao: RecipeDao
}
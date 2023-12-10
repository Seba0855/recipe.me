package pl.smcebi.recipeme.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.smcebi.recipeme.database.DatabaseConstants.COLUMN_RECIPE_DURATION_AND_SERVINGS
import pl.smcebi.recipeme.database.DatabaseConstants.COLUMN_RECIPE_ENTITY_ID
import pl.smcebi.recipeme.database.DatabaseConstants.COLUMN_RECIPE_ID
import pl.smcebi.recipeme.database.DatabaseConstants.COLUMN_RECIPE_IMAGE_URL
import pl.smcebi.recipeme.database.DatabaseConstants.COLUMN_RECIPE_TITLE
import pl.smcebi.recipeme.database.DatabaseConstants.TABLE_RECIPE

@Entity(tableName = TABLE_RECIPE)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_RECIPE_ID)
    val recipeId: String,
    @ColumnInfo(name = COLUMN_RECIPE_TITLE)
    val title: String,
    @ColumnInfo(name = COLUMN_RECIPE_IMAGE_URL)
    val imageUrl: String,
    @ColumnInfo(name = COLUMN_RECIPE_DURATION_AND_SERVINGS)
    val durationAndServings: String
)
package pl.smcebi.recipeme.domain.recipes.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RecipesUI(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val readyInMinutes: Int,
    val servings: Int,
    val durationAndServings: String,
    val dishType: String? = null,
    val ingredientsList: List<IngredientUI>,
) : Parcelable

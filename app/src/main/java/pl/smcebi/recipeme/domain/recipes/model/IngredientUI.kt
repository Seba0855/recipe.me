package pl.smcebi.recipeme.domain.recipes.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class IngredientUI(
    val id: Long,
    val name: String,
    val imageUrl: String?,
    val amount: Double,
    val unit: String,
) : Parcelable
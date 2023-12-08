package pl.smcebi.recipeme.ui.home.details

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI

@Keep
internal sealed interface RecipeDetailsEvent : Parcelable {
    @Keep
    @Parcelize
    data class NavigateInstructions(val recipe: RecipesUI) : RecipeDetailsEvent
}
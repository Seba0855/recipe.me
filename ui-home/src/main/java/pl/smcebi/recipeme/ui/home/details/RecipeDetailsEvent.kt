package pl.smcebi.recipeme.ui.home.details

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI

internal sealed interface RecipeDetailsEvent {

    data object ShowRecipeSavedMessage : RecipeDetailsEvent
    data class NavigateInstructions(val recipe: RecipesUI) : RecipeDetailsEvent
    @JvmInline
    value class ShowError(val message: String?) : RecipeDetailsEvent
}
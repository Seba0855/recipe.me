package pl.smcebi.recipeme.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.smcebi.recipeme.domain.recipes.GetRecipeNutritionUseCase
import pl.smcebi.recipeme.ui.common.extensions.mutate
import pl.smcebi.recipeme.ui.common.extensions.withProgressBar
import javax.inject.Inject

@HiltViewModel
internal class RecipeDetailsViewModel @Inject constructor(
    getRecipeNutritionUseCase: GetRecipeNutritionUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val mutableState = MutableStateFlow(RecipeDetailsState())
    val state: StateFlow<RecipeDetailsState> = mutableState.asStateFlow()

    private val recipe = RecipeDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle).recipe

    init {
        withProgressBar(indicator = { inProgress ->
            mutableState.mutate {
                copy(nutritionInProgress = inProgress)
            }
        }) {
            getRecipeNutritionUseCase(recipe.id.toString()).onSuccess { nutrition ->
                mutableState.mutate {
                    copy(
                        calories = nutrition.calories,
                        carbs = nutrition.carbs,
                        fat = nutrition.fat,
                        protein = nutrition.protein,
                    )
                }
            }
        }
    }
}
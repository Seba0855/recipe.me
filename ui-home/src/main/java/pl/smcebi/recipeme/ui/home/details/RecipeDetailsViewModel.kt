package pl.smcebi.recipeme.ui.home.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.smcebi.recipeme.domain.recipes.GetRecipeByIdUseCase
import pl.smcebi.recipeme.domain.recipes.GetRecipeNutritionUseCase
import pl.smcebi.recipeme.ui.common.extensions.EventsChannel
import pl.smcebi.recipeme.ui.common.extensions.mutate
import pl.smcebi.recipeme.ui.common.extensions.withProgressBar
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class RecipeDetailsViewModel @Inject constructor(
    private val getRecipeNutritionUseCase: GetRecipeNutritionUseCase,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val mutableState = MutableStateFlow(RecipeDetailsState())
    val state: StateFlow<RecipeDetailsState> = mutableState.asStateFlow()

    private val mutableEvent = EventsChannel<RecipeDetailsEvent>()
    val event: Flow<RecipeDetailsEvent> = mutableEvent.receiveAsFlow()

    private val args = RecipeDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        initializeFetch()
    }

    fun navigateRecipeInstructions() {
        state.value.recipe?.let { recipe ->
            mutableEvent.trySend(RecipeDetailsEvent.NavigateInstructions(recipe))
        }
    }

    fun onTryAgainClicked() {
        mutableState.mutate { copy(isError = false) }
        initializeFetch()
    }

    private fun initializeFetch() {
        if (args.recipe != null) {
            mutableState.mutate {
                copy(recipe = args.recipe)
            }
            args.recipe?.id?.let(::fetchRecipeNutrition)
        } else {
            // TODO: Nutrition data can be fetched from one endpoint, there is no need to call both of them
            //  it requires some ifology but it is a quota optimization. For now every search recipe details consumes 2 points.
            fetchRecipe()
            args.recipeId.let(::fetchRecipeNutrition)
        }
    }

    private fun fetchRecipeNutrition(recipeId: String) {
        withProgressBar(indicator = { inProgress ->
            mutableState.mutate {
                copy(nutritionInProgress = inProgress)
            }
        }) {
            getRecipeNutritionUseCase(recipeId).onSuccess { nutrition ->
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

    private fun fetchRecipe() {
        withProgressBar(indicator = { inProgress ->
            mutableState.mutate {
                copy(inProgress = inProgress)
            }
        }) {
            delay(TRANSITION_DELAY)
            getRecipeByIdUseCase(args.recipeId)
                .onSuccess { recipe ->
                    mutableState.mutate { copy(recipe = recipe) }
                }
                .onFailure { errorMessage ->
                    mutableState.mutate { copy(isError = true) }
                    mutableEvent.send(RecipeDetailsEvent.ShowError(errorMessage))
                }
        }
    }

    companion object {
        const val TRANSITION_DELAY = 500L
    }
}

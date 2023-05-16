package pl.smcebi.recipeme.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.smcebi.recipeme.domain.recipes.GetQuickAnswerUseCase
import pl.smcebi.recipeme.domain.recipes.GetRandomRecipesUseCase
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import pl.smcebi.recipeme.ui.common.extensions.EventsChannel
import pl.smcebi.recipeme.ui.common.extensions.mutate
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase,
    private val getQuickAnswerUseCase: GetQuickAnswerUseCase
) : ViewModel() {

    private val mutableState = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> = mutableState.asStateFlow()

    private val mutableEvent = EventsChannel<HomeViewEvent>()
    val event: Flow<HomeViewEvent> = mutableEvent.receiveAsFlow()

    init {
        val mockRecipes = listOf(
            RecipesUI(
                id = 1,
                title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
                imageUrl = "https://spoonacular.com/recipeImages/716429-556x370.jpg",
                readyInMinutes = 69,
                servings = 2,
                durationAndServings = "69 Minutes | 2 servings "
            ),
            RecipesUI(
                id = 2,
                title = "Summery Tomato Soup with Pasta and Chickpeas",
                imageUrl = "https://spoonacular.com/recipeImages/794351-556x370.jpg",
                readyInMinutes = 69,
                servings = 2,
                durationAndServings = "69 Minutes | 2 servings "
            ),
            RecipesUI(
                id = 3,
                title = "Creamy tomato soup",
                imageUrl = "https://spoonacular.com/recipeImages/640713-556x370.jpg",
                readyInMinutes = 69,
                servings = 2,
                durationAndServings = "69 Minutes | 2 servings "
            )
        )

        mutableState.mutate {
            copy(recipes = mockRecipes)
        }
    }

//    fun getRecipes() {
//        viewModelScope.launch {
//            getRandomRecipesUseCase(tags = "chicken pasta")
//                .onSuccess { recipes ->
//                    mutableState.mutate {
//                        copy(recipes = recipes)
//                    }
//                }
//                .onFailure { message ->
//                    Timber.e("Error: $message")
//                    mutableEvent.send(HomeViewEvent.ShowError(message))
//                }
//        }
//    }

    fun getQuickAnswer() {
        viewModelScope.launch {
            getQuickAnswerUseCase(query = "How much vitamin c is in 2 apples?")
                .onSuccess { quickAnswerUI ->
                    Timber.d("Answer: ${quickAnswerUI.answer} Image: ${quickAnswerUI.image}")
                }
                .onFailure { message ->
                    Timber.e("Error: $message")
                    mutableEvent.send(HomeViewEvent.ShowError(message))
                }
        }
    }
}

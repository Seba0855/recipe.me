package pl.smcebi.recipeme.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.smcebi.recipeme.domain.recipes.GetQuickAnswerUseCase
import pl.smcebi.recipeme.domain.recipes.GetRandomRecipesUseCase
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

    fun getRecipes() {
        viewModelScope.launch {
            getRandomRecipesUseCase(tags = "garlic")
                .onSuccess { recipes ->
                    Timber.d("Recipe title: ${recipes[0].title}")
                    mutableState.mutate {
                        copy(title = recipes[0].title)
                    }
                }
                .onFailure { message ->
                    Timber.e("Error: $message")
                    mutableEvent.send(HomeViewEvent.ShowError(message))
                }
        }
    }

    fun getQuickAnswer() {
        viewModelScope.launch {
            getQuickAnswerUseCase(query = "How much vitamin c is in 2 apples?")
                .onSuccess { quickAnswerUI ->
                    Timber.d("Answer: ${quickAnswerUI.answer} Image: ${quickAnswerUI.image}")
                    mutableState.mutate {
                        copy(title = quickAnswerUI.answer)
                    }
                }
                .onFailure { message ->
                    Timber.e("Error: $message")
                    mutableEvent.send(HomeViewEvent.ShowError(message))
                }
        }
    }
}

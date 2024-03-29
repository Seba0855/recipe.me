package pl.smcebi.recipeme.ui.home.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.smcebi.recipeme.domain.common.translation.TranslateTextUseCase
import pl.smcebi.recipeme.domain.common.utils.Selectable.Companion.toSelectable
import pl.smcebi.recipeme.domain.recipes.search.GetAutocompletedRecipesUseCase
import pl.smcebi.recipeme.domain.recipes.GetRandomRecipesUseCase
import pl.smcebi.recipeme.domain.recipes.model.MealType
import pl.smcebi.recipeme.domain.recipes.store.SaveRecipeUseCase
import pl.smcebi.recipeme.ui.common.BottomNavCommunicationBridge
import pl.smcebi.recipeme.ui.common.extensions.EventsChannel
import pl.smcebi.recipeme.ui.common.extensions.mutate
import pl.smcebi.recipeme.ui.common.extensions.withProgressBar
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase,
    private val translateTextUseCase: TranslateTextUseCase,
    private val getAutocompletedRecipesUseCase: Lazy<GetAutocompletedRecipesUseCase>,
    private val bottomNavCommunicationBridge: Lazy<BottomNavCommunicationBridge>,
    private val saveRecipeUseCase: SaveRecipeUseCase,
) : ViewModel() {

    private val mutableState = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> = mutableState.asStateFlow()

    private val mutableEvent = EventsChannel<HomeViewEvent>()
    val event: Flow<HomeViewEvent> = mutableEvent.receiveAsFlow()

    init {
        fetchRecipes()
    }

    fun tryAgain() {
        mutableState.mutate { copy(isError = false) }
        fetchRecipes()
    }

    fun onBookmarkClick(position: Int) {
        viewModelScope.launch {
            saveRecipeUseCase(state.value.recipes[position]).onSuccess {
                mutableEvent.send(HomeViewEvent.ShowSavedRecipeMessage)
            }
        }
    }

    fun onNewMealTypeSelected(position: Int) {
        mutableState.mutate {
            copy(mealTypeEntries = MealType.entries.toSelectable(position))
        }
        fetchRecipes(MealType.entries[position])
    }

    fun onSearchRequested(query: String) {
        withProgressBar(indicator = { inProgress ->
            mutableState.mutate {
                copy(searchInProgress = inProgress)
            }
        }) {
            clearSuggestions()
            getAutocompletedRecipesUseCase.get()(query)
                .onSuccess { suggestions ->
                    mutableState.mutate {
                        copy(
                            showInitialMessage = false,
                            noSearchResult = suggestions.isEmpty(),
                            searchSuggestions = suggestions
                        )
                    }
                    Timber.d("Suggestions: $suggestions")
                }
        }
    }

    fun onSuggestionClick(position: Int) {
        val suggestionId = state.value.searchSuggestions[position].id
        mutableEvent.trySend(HomeViewEvent.NavigateDetails(suggestionId))
    }

    fun clearSuggestions() {
        mutableState.mutate {
            copy(
                showInitialMessage = true,
                noSearchResult = false,
                searchSuggestions = emptyList()
            )
        }
    }

    fun manageBottomNavVisibility(isVisible: Boolean) {
        bottomNavCommunicationBridge.get().mutateState(isVisible)
    }

    private fun fetchRecipes(mealType: MealType = MealType.RANDOM) {
        withProgressBar(indicator = { inProgress ->
            mutableState.mutate {
                copy(inProgress = inProgress)
            }
        }) {
            getRandomRecipesUseCase(mealType)
                .onSuccess { recipes ->
                    mutableState.mutate {
                        copy(recipes = recipes)
                    }
                }
                .onFailure { message ->
                    Timber.e("Error: $message")
                    mutableState.mutate { copy(isError = true) }
                    mutableEvent.send(HomeViewEvent.ShowError(message))
                }
        }
    }
}

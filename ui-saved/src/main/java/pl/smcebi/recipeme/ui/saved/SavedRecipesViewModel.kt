package pl.smcebi.recipeme.ui.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.smcebi.recipeme.domain.recipes.store.CollectStoredRecipesUseCase
import pl.smcebi.recipeme.domain.recipes.store.RemoveStoredRecipeUseCase
import pl.smcebi.recipeme.ui.common.extensions.EventsChannel
import pl.smcebi.recipeme.ui.common.extensions.mutate
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class SavedRecipesViewModel @Inject constructor(
    collectStoredRecipesUseCase: CollectStoredRecipesUseCase,
    private val removeStoredRecipeUseCase: RemoveStoredRecipeUseCase,
) : ViewModel() {

    private val mutableState = MutableStateFlow(SavedRecipesState())
    val state: StateFlow<SavedRecipesState> = mutableState.asStateFlow()

    private val mutableEvent = EventsChannel<SavedRecipesEvent>()
    val event: Flow<SavedRecipesEvent> = mutableEvent.receiveAsFlow()

    init {
        collectStoredRecipesUseCase().onEach { recipes ->
            mutableState.mutate {
                copy(recipes = recipes)
            }
        }.launchIn(viewModelScope)
    }

    fun navigateDetails(position: Int) {
        mutableEvent.trySend(SavedRecipesEvent.NavigateDetails(state.value.recipes[position].id))
    }

    fun removeStoredRecipe(position: Int) {
        viewModelScope.launch {
            removeStoredRecipeUseCase(state.value.recipes[position]).onSuccess {
                mutableEvent.send(SavedRecipesEvent.ShowRecipeRemovedMessage)
            }
        }
    }
}
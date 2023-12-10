package pl.smcebi.recipeme.ui.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pl.smcebi.recipeme.domain.recipes.store.CollectStoredRecipesUseCase
import pl.smcebi.recipeme.ui.common.extensions.mutate
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class SavedRecipesViewModel @Inject constructor(
    private val collectStoredRecipesUseCase: CollectStoredRecipesUseCase,
) : ViewModel() {

    private val mutableState = MutableStateFlow(SavedRecipesState())
    val state: StateFlow<SavedRecipesState> = mutableState.asStateFlow()

    init {
        collectStoredRecipesUseCase().onEach { recipes ->
            Timber.d("Saved recipes: $recipes")
            mutableState.mutate {
                copy(recipes = recipes)
            }
        }.launchIn(viewModelScope)
    }
}
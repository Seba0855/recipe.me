package pl.smcebi.recipeme.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.smcebi.recipeme.domain.recipes.GetRandomRecipesUseCase
import pl.smcebi.recipeme.ui.common.extensions.EventsChannel
import pl.smcebi.recipeme.ui.common.extensions.mutate

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase
) : ViewModel() {

    private val mutableState = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> = mutableState.asStateFlow()

    private val mutableEvent = EventsChannel<HomeViewEvent>()
    val event: Flow<HomeViewEvent> = mutableEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            getRandomRecipesUseCase(tags = "garlic")
                .onSuccess { recipes ->
                    mutableState.mutate {
                        copy(title = recipes.title)
                    }
                }
                .onFailure { message ->
                    mutableEvent.send(HomeViewEvent.ShowError(message))
                }
        }
    }
}

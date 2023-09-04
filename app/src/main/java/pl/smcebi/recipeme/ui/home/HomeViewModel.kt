package pl.smcebi.recipeme.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import pl.smcebi.recipeme.domain.recipes.GetRandomRecipesUseCase
import pl.smcebi.recipeme.ui.common.extensions.EventsChannel
import pl.smcebi.recipeme.ui.common.extensions.mutate
import pl.smcebi.recipeme.ui.common.extensions.withProgressBar
import timber.log.Timber

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase
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
        TODO("Not implemented")
    }

    private fun fetchRecipes() {
        withProgressBar(indicator = { inProgress ->
            mutableState.mutate {
                copy(inProgress = inProgress)
            }
        }) {
            getRandomRecipesUseCase()
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

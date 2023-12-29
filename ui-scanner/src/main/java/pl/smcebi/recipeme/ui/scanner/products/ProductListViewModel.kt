package pl.smcebi.recipeme.ui.scanner.products

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
import pl.smcebi.domain.products.store.CollectStoredProductsUseCase
import pl.smcebi.domain.products.store.RemoveStoredProductUseCase
import pl.smcebi.recipeme.ui.common.extensions.EventsChannel
import pl.smcebi.recipeme.ui.common.extensions.mutate
import javax.inject.Inject

@HiltViewModel
internal class ProductListViewModel @Inject constructor(
    collectStoredProductsUseCase: CollectStoredProductsUseCase,
    private val removeStoredProductsUseCase: RemoveStoredProductUseCase,
) : ViewModel() {

    private val mutableState = MutableStateFlow(ProductListState())
    val state: StateFlow<ProductListState> = mutableState.asStateFlow()

    private val mutableEvent = EventsChannel<ProductListEvent>()
    val event: Flow<ProductListEvent> = mutableEvent.receiveAsFlow()

    init {
        collectStoredProductsUseCase().onEach { products ->
            mutableState.mutate {
                copy(products = products)
            }
        }.launchIn(viewModelScope)
    }

    fun removeStoredProduct(position: Int) {
        viewModelScope.launch {
            removeStoredProductsUseCase(state.value.products[position]).onSuccess {
                mutableEvent.send(ProductListEvent.ShowProductRemovedMessage)
            }
        }
    }
}
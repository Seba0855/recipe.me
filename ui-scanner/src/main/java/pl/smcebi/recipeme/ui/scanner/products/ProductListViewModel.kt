package pl.smcebi.recipeme.ui.scanner.products

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class ProductListViewModel @Inject constructor() : ViewModel() {

    private val mutableState = MutableStateFlow(ProductListState())
    val state: StateFlow<ProductListState> = mutableState.asStateFlow()

    init {

    }
}
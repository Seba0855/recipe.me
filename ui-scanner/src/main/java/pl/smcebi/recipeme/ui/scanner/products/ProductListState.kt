package pl.smcebi.recipeme.ui.scanner.products

import pl.smcebi.domain.products.ProductUI

internal data class ProductListState(
    val products: List<pl.smcebi.domain.products.ProductUI> = emptyList()
)

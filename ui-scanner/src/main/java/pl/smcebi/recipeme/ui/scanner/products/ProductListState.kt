package pl.smcebi.recipeme.ui.scanner.products

import pl.smcebi.recipeme.domain.common.products.ProductUI

internal data class ProductListState(
    val products: List<ProductUI> = emptyList()
)

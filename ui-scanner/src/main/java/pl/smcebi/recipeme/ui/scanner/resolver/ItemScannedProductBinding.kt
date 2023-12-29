package pl.smcebi.recipeme.ui.scanner.resolver

import pl.smcebi.domain.products.ProductUI
import pl.smcebi.recipeme.ui.common.extensions.load
import pl.smcebi.recipeme.ui.scanner.databinding.ItemScannedProductBinding
import pl.smcebi.recipeme.uicommon.R.drawable.ic_vegetables

internal fun ItemScannedProductBinding.bind(product: pl.smcebi.domain.products.ProductUI) {
    productNameTextView.text = product.name
    productBrandTextView.text = product.brand
    productImageView.load(data = product.imageUrl, fallback = ic_vegetables)
}
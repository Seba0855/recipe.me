package pl.smcebi.recipeme.ui.scanner.resolver

import pl.smcebi.domain.products.ProductUI

internal sealed interface ProductResolverEvent {
    data object StopImageAnalysis : ProductResolverEvent
    data object ResumeImageAnalysis : ProductResolverEvent
    data object DismissProduct : ProductResolverEvent
    data object ShowProductSavedAnimation : ProductResolverEvent
    @JvmInline value class ShowProduct(val product: ProductUI) : ProductResolverEvent
    @JvmInline value class ShowError(val message: String?) : ProductResolverEvent
}
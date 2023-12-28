package pl.smcebi.recipeme.ui.scanner.resolver

import pl.smcebi.recipeme.domain.common.products.ProductUI

internal sealed interface ProductResolverEvent {
    data object StopImageAnalysis : ProductResolverEvent
    data object ResumeImageAnalysis : ProductResolverEvent
    data object DismissProduct : ProductResolverEvent
    @JvmInline value class ShowProduct(val product: ProductUI) : ProductResolverEvent
    @JvmInline value class ShowError(val message: String?) : ProductResolverEvent
}
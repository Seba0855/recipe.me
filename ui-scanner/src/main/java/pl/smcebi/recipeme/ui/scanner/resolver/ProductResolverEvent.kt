package pl.smcebi.recipeme.ui.scanner.resolver

internal sealed interface ProductResolverEvent {
    object StopImageAnalysis : ProductResolverEvent
    object ResumeImageAnalysis : ProductResolverEvent
    @JvmInline value class ShowProductName(val name: String) : ProductResolverEvent
    @JvmInline value class ShowError(val message: String?) : ProductResolverEvent
}
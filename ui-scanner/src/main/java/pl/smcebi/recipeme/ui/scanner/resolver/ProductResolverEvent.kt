package pl.smcebi.recipeme.ui.scanner.resolver

internal sealed class ProductResolverEvent {
    object StopImageAnalysis : ProductResolverEvent()
    object ResumeImageAnalysis : ProductResolverEvent()
}
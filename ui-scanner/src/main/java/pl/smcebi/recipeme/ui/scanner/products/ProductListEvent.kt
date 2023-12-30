package pl.smcebi.recipeme.ui.scanner.products

internal sealed interface ProductListEvent {
    @JvmInline
    value class NavigateDetails(val recipeId: String) : ProductListEvent
    data object ShowProductRemovedMessage : ProductListEvent
}
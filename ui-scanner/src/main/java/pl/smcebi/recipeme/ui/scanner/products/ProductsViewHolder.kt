package pl.smcebi.recipeme.ui.scanner.products

import androidx.recyclerview.widget.RecyclerView
import pl.smcebi.domain.products.ProductUI
import pl.smcebi.recipeme.ui.common.extensions.load
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener
import pl.smcebi.recipeme.ui.scanner.R
import pl.smcebi.recipeme.ui.scanner.databinding.ItemListedProductBinding
import pl.smcebi.recipeme.uicommon.R.drawable.ic_vegetables

internal typealias OnItemClick = (Int) -> Unit

internal class ProductsViewHolder(
    private val binding: ItemListedProductBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: ProductUI) {
        with(binding) {
            productImageView.load(
                data = product.imageUrl,
                fallback = ic_vegetables
            )
            productBrandTextView.text = product.brand
            productNameTextView.text = product.name
        }
    }

    fun setOnDeleteClickListener(listener: OnItemClick) {
        binding.openArrow.setSafeOnClickListener {
            listener(bindingAdapterPosition)
        }
    }
}
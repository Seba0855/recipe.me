package pl.smcebi.recipeme.ui.scanner.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.smcebi.domain.products.ProductUI
import pl.smcebi.recipeme.ui.scanner.databinding.ItemListedProductBinding

internal class ProductsAdapter(
    onDeleteClick: OnItemClick
) : ListAdapter<ProductUI, ProductsViewHolder>(DIFF_UTIL) {

    private var onDeleteClickListener: OnItemClick? = onDeleteClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        ProductsViewHolder(
            ItemListedProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewAttachedToWindow(holder: ProductsViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.setOnDeleteClickListener { position ->
            onDeleteClickListener?.invoke(position)
            notifyItemChanged(position)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        onDeleteClickListener = null
        super.onDetachedFromRecyclerView(recyclerView)
    }

    private companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ProductUI>() {
            override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean =
                oldItem == newItem
        }
    }
}
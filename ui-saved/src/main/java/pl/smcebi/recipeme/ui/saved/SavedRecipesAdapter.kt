package pl.smcebi.recipeme.ui.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import pl.smcebi.recipeme.ui.saved.databinding.ItemSavedRecipeCardBinding

internal class SavedRecipesAdapter(
    onRecipeClick: OnItemViewClick,
    onBookmarkClick: OnItemClick
) : ListAdapter<RecipesUI, SavedRecipesViewHolder>(RecipeDiffUtil()) {

    private var onRecipeClickListener: OnItemViewClick? = onRecipeClick
    private var onBookmarkClickListener: OnItemClick? = onBookmarkClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRecipesViewHolder =
        SavedRecipesViewHolder(
            ItemSavedRecipeCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        onBookmarkClickListener = null
        onRecipeClickListener = null
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: SavedRecipesViewHolder) {
        super.onViewAttachedToWindow(holder)
        with(holder) {
            setOnRecipeClickListener { transitioningView, position ->
                onRecipeClickListener?.invoke(transitioningView, position)
            }
            setOnBookmarkClickListener { position ->
                onBookmarkClickListener?.invoke(position)
            }
        }
    }

    override fun onBindViewHolder(holder: SavedRecipesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    internal class RecipeDiffUtil : DiffUtil.ItemCallback<RecipesUI>() {
        override fun areItemsTheSame(oldItem: RecipesUI, newItem: RecipesUI): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RecipesUI, newItem: RecipesUI): Boolean =
            oldItem == newItem
    }
}

package pl.smcebi.recipeme.ui.home.main.suggestions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.smcebi.recipeme.domain.recipes.model.SuggestionUI
import pl.smcebi.recipeme.ui.home.databinding.ItemSearchItemBinding

internal class SuggestionsAdapter(
    onSuggestionClick: OnItemClick,
) : ListAdapter<SuggestionUI, SuggestionsViewHolder>(DIFF_UTIL) {

    private var onSuggestionClick: OnItemClick? = onSuggestionClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionsViewHolder =
        SuggestionsViewHolder(
            ItemSearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SuggestionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewAttachedToWindow(holder: SuggestionsViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.setOnSuggestionClickListener { position ->
            onSuggestionClick?.invoke(position)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        onSuggestionClick = null
        super.onDetachedFromRecyclerView(recyclerView)
    }

    private companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<SuggestionUI>() {
            override fun areItemsTheSame(oldItem: SuggestionUI, newItem: SuggestionUI): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SuggestionUI, newItem: SuggestionUI): Boolean =
                oldItem == newItem
        }
    }
}
package pl.smcebi.recipeme.ui.home.main.mealtype

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.smcebi.recipeme.domain.recipes.model.MealType
import pl.smcebi.recipeme.ui.home.databinding.ItemMealtypeButtonBinding
import pl.smcebi.recipeme.ui.home.main.suggestions.OnItemClick

internal class MealtypeAdapter(
    onMealtypeClick: OnItemClick
) : ListAdapter<MealType, MealtypeViewHolder>(DIFF_UTIL) {

    private var onMealtypeclick: OnItemClick? = onMealtypeClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealtypeViewHolder =
        MealtypeViewHolder(
            ItemMealtypeButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MealtypeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewAttachedToWindow(holder: MealtypeViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.setOnMealtypeClickListener { position ->
            onMealtypeclick?.invoke(position)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        onMealtypeclick = null
        super.onDetachedFromRecyclerView(recyclerView)
    }

    private companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<MealType>() {
            override fun areItemsTheSame(oldItem: MealType, newItem: MealType): Boolean =
                oldItem.externalName == newItem.externalName

            override fun areContentsTheSame(oldItem: MealType, newItem: MealType): Boolean =
                oldItem == newItem
        }
    }
}
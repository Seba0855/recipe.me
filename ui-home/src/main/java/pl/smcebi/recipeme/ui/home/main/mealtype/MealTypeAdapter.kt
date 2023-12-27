package pl.smcebi.recipeme.ui.home.main.mealtype

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.smcebi.recipeme.domain.recipes.model.MealType
import pl.smcebi.recipeme.ui.home.databinding.ItemMealtypeButtonBinding
import pl.smcebi.recipeme.ui.home.main.suggestions.OnItemClick

internal class MealTypeAdapter(
    onMealTypeClick: OnItemClick
) : ListAdapter<MealType, MealTypeViewHolder>(DIFF_UTIL) {

    private var onMealTypeClick: OnItemClick? = onMealTypeClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealTypeViewHolder =
        MealTypeViewHolder(
            ItemMealtypeButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MealTypeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewAttachedToWindow(holder: MealTypeViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.setOnMealTypeClickListener { position ->
            onMealTypeClick?.invoke(position)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        onMealTypeClick = null
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
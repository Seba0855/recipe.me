package pl.smcebi.recipeme.ui.home.details.instructions

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.ui.common.extensions.load
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding
import pl.smcebi.recipeme.ui.home.R
import pl.smcebi.recipeme.ui.home.databinding.FragmentRecipeInstructionsBinding
import pl.smcebi.recipeme.uicommon.R.drawable.recipe_placeholder

@AndroidEntryPoint
internal class RecipeInstructionsFragment : Fragment(R.layout.fragment_recipe_instructions) {

    private val binding by viewBinding(FragmentRecipeInstructionsBinding::bind)
    private val args: RecipeInstructionsFragmentArgs by navArgs()
    private var adapter: RecipeInstructionsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enterTransition = MaterialFade()

        initViews()
    }

    private fun initViews() {
        with(binding) {
            val recipe = args.recipe

            backButton.apply {
                setSafeOnClickListener {
                    findNavController().navigateUp()
                }
                clipToOutline = true
            }

            adapter = RecipeInstructionsAdapter()
            instructionsRecyclerView.adapter = adapter

            recipeImage.load(data = recipe.imageUrl, fallback = recipe_placeholder)

            instructionsHeadline.transitionName = recipe.title

            recipeNameTextView.text = recipe.title
            dishTypeTextView.isVisible = recipe.dishType != null
            dishTypeTextView.text = getString(R.string.fragment_details_dish_type, recipe.dishType)
            durationTextView.text =
                getString(R.string.fragment_details_duration, recipe.readyInMinutes.toString())
            servingsTextView.text =
                getString(R.string.fragment_details_meal_servings, recipe.servings.toString())

            adapter?.submitList(recipe.instructions)
        }
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}

package pl.smcebi.recipeme.ui.common.details

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.ui.common.extensions.collectOnViewLifecycle
import pl.smcebi.recipeme.ui.common.extensions.load
import pl.smcebi.recipeme.ui.common.extensions.notImplemented
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding
import pl.smcebi.recipeme.uicommon.R
import pl.smcebi.recipeme.uicommon.databinding.FragmentRecipeDetailsBinding

@AndroidEntryPoint
internal class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {
    private val binding by viewBinding(FragmentRecipeDetailsBinding::bind)
    private val args: RecipeDetailsFragmentArgs by navArgs()
    private val viewModel: RecipeDetailsViewModel by viewModels()
    private var adapter: IngredientsAdapter? = null
    private var descriptionExpanded: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform()
        initViews()
        collectOnViewLifecycle(viewModel.state, ::onNewState)
    }

    private fun initViews() {
        with(binding) {
            adapter = IngredientsAdapter()
            val recipe = args.recipe
            recipeImage.load(data = recipe.imageUrl, fallback = R.drawable.recipe_placeholder)
            backButton.setSafeOnClickListener {
                findNavController().navigateUp()
            }
            saveButton.setSafeOnClickListener {
                notImplemented()
            }
            showMoreButton.setSafeOnClickListener {
                if (!descriptionExpanded) {
                    descriptionTextView.maxLines = Int.MAX_VALUE
                } else {
                    descriptionTextView.maxLines =
                        DESCRIPTION_COLLAPSED_LINES
                }

                descriptionExpanded = !descriptionExpanded

                showMoreButton.text = if (descriptionExpanded) {
                    getString(R.string.fragment_details_show_less)
                } else {
                    getString(R.string.fragment_details_show_more)
                }
            }

            imageContainer.transitionName = recipe.imageUrl
            recipeNameTextView.text = recipe.title
            dishTypeTextView.isVisible = recipe.dishType != null
            dishTypeTextView.text = getString(R.string.fragment_details_dish_type, recipe.dishType)
            durationTextView.text =
                getString(R.string.fragment_details_duration, recipe.readyInMinutes.toString())

            descriptionTextView.text =
                Html.fromHtml(recipe.description, Html.FROM_HTML_OPTION_USE_CSS_COLORS)
            servingsTextView.text =
                getString(R.string.fragment_details_meal_servings, recipe.servings.toString())

            ingredientsRecyclerView.adapter = adapter
            adapter?.submitList(recipe.ingredientsList)
        }
    }

    private fun onNewState(state: RecipeDetailsState) {
        with(binding) {
            caloriesTextView.text = state.calories
            proteinsTextView.text = state.protein
            fatsTextView.text = state.fat
            carbohydratesTextView.text = state.carbs
        }
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }

    private companion object {
        const val DESCRIPTION_COLLAPSED_LINES = 4
    }
}

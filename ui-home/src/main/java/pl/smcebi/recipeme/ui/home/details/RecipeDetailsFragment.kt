package pl.smcebi.recipeme.ui.home.details

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import pl.smcebi.recipeme.ui.common.extensions.collectOnViewLifecycle
import pl.smcebi.recipeme.ui.common.extensions.load
import pl.smcebi.recipeme.ui.common.extensions.notImplemented
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding
import pl.smcebi.recipeme.ui.home.R
import pl.smcebi.recipeme.ui.home.databinding.FragmentRecipeDetailsBinding
import pl.smcebi.recipeme.uicommon.R.drawable.recipe_placeholder

@AndroidEntryPoint
internal class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {
    private val binding by viewBinding(FragmentRecipeDetailsBinding::bind)
    private val viewModel: RecipeDetailsViewModel by viewModels()
    private var adapter: IngredientsAdapter? = null
    private var descriptionExpanded: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reenterTransition = MaterialElevationScale(true)
        sharedElementEnterTransition = MaterialContainerTransform()

        initViews()
        collectOnViewLifecycle(viewModel.state, ::onNewState)
        collectOnViewLifecycle(viewModel.event, ::onNewEvent)
    }

    private fun initViews() {
        with(binding) {
            adapter = IngredientsAdapter()

            backButton.apply {
                setSafeOnClickListener {
                    findNavController().navigateUp()
                }
                clipToOutline = true
            }
            saveButton.apply {
                setSafeOnClickListener {
                    notImplemented()
                }
                clipToOutline = true
            }
            showMoreButton.setSafeOnClickListener {
                onShowMoreButtonClicked()
            }
            checkInstructionsButton.setSafeOnClickListener {
                viewModel.navigateRecipeInstructions()
            }

            ingredientsRecyclerView.adapter = adapter
        }
    }

    private fun onNewState(state: RecipeDetailsState) {
        with(binding) {
            caloriesTextView.text = state.calories
            proteinsTextView.text = state.protein
            fatsTextView.text = state.fat
            carbohydratesTextView.text = state.carbs
            checkInstructionsButton.isGone = state.recipe?.instructions?.isEmpty() == true

            state.recipe?.let { recipe ->
                recipeImage.load(data = recipe.imageUrl, fallback = recipe_placeholder)
                imageContainer.transitionName = recipe.imageUrl
                descriptionHeader.transitionName = recipe.title
                recipeNameTextView.text = recipe.title
                dishTypeTextView.isVisible = recipe.dishType != null
                dishTypeTextView.text =
                    getString(R.string.fragment_details_dish_type, recipe.dishType)
                durationTextView.text =
                    getString(R.string.fragment_details_duration, recipe.readyInMinutes.toString())

                descriptionTextView.text =
                    Html.fromHtml(recipe.description, Html.FROM_HTML_OPTION_USE_CSS_COLORS)
                servingsTextView.text =
                    getString(R.string.fragment_details_meal_servings, recipe.servings.toString())

                adapter?.submitList(state.recipe.ingredientsList)
            }
        }
    }

    private fun onNewEvent(event: RecipeDetailsEvent) {
        when (event) {
            is RecipeDetailsEvent.NavigateInstructions -> navigateInstructions(event.recipe)
        }
    }

    private fun onShowMoreButtonClicked() {
        with(binding) {
            if (!descriptionExpanded) {
                descriptionTextView.maxLines = Int.MAX_VALUE
            } else {
                descriptionTextView.maxLines = DESCRIPTION_COLLAPSED_LINES
            }

            descriptionExpanded = !descriptionExpanded

            showMoreButton.text = if (descriptionExpanded) {
                getString(R.string.fragment_details_show_less)
            } else {
                getString(R.string.fragment_details_show_more)
            }
        }
    }

    private fun navigateInstructions(recipe: RecipesUI) {
        findNavController().navigate(
            directions = RecipeDetailsFragmentDirections.navigateRecipeInstructions(recipe),
            navigatorExtras = FragmentNavigatorExtras(
                binding.descriptionHeader to binding.descriptionHeader.transitionName
            )
        )
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }

    private companion object {
        const val DESCRIPTION_COLLAPSED_LINES = 4
    }
}

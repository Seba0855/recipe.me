package pl.smcebi.recipeme.ui.home.details

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.R.attr.colorSurface
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import pl.smcebi.recipeme.ui.common.extensions.collectOnViewLifecycle
import pl.smcebi.recipeme.ui.common.extensions.getThemedColor
import pl.smcebi.recipeme.ui.common.extensions.load
import pl.smcebi.recipeme.ui.common.extensions.notImplemented
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener
import pl.smcebi.recipeme.ui.common.extensions.showSnackbar
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding
import pl.smcebi.recipeme.ui.home.R
import pl.smcebi.recipeme.ui.home.databinding.FragmentRecipeDetailsBinding
import pl.smcebi.recipeme.ui.home.main.HomeViewEvent
import pl.smcebi.recipeme.uicommon.R.drawable.recipe_placeholder

@AndroidEntryPoint
internal class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {
    private val binding by viewBinding(FragmentRecipeDetailsBinding::bind)
    private val viewModel: RecipeDetailsViewModel by viewModels()
    private var adapter: IngredientsAdapter? = null
    private var descriptionExpanded: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enterTransition = MaterialFade()
        reenterTransition = MaterialElevationScale(true)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
        }

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
                    viewModel.onSaveButtonClicked()
                }
                clipToOutline = true
            }
            showMoreButton.setSafeOnClickListener {
                onShowMoreButtonClicked()
            }
            checkInstructionsButton.setSafeOnClickListener {
                viewModel.navigateRecipeInstructions()
            }
            connectionError.tryAgainButton.setSafeOnClickListener {
                viewModel.onTryAgainClicked()
            }

            ingredientsRecyclerView.adapter = adapter
        }
    }

    private fun onNewState(state: RecipeDetailsState) {
        with(binding) {
            connectionError.root.isVisible = state.isError
            caloriesTextView.text = state.calories
            proteinsTextView.text = state.protein
            fatsTextView.text = state.fat
            carbohydratesTextView.text = state.carbs
            checkInstructionsButton.isGone =
                state.recipe?.instructions?.isEmpty() == true || state.inProgress || state.isError

            recipeImage.isGone = state.inProgress
            imageLoading.root.isVisible = state.inProgress
            descriptionLoading.root.isVisible = state.inProgress
            descriptionTextView.isInvisible = state.inProgress
            showMoreButton.isInvisible = state.inProgress
            nutritionLoading.root.isVisible = state.nutritionInProgress || state.inProgress
            nutritionGroup.isInvisible = state.nutritionInProgress || state.inProgress
            ingredientsLoading.root.isVisible = state.inProgress
            ingredientsRecyclerView.isGone = state.inProgress

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

                adapter?.submitList(recipe.ingredientsList)
            }
        }
    }

    private fun onNewEvent(event: RecipeDetailsEvent) {
        when (event) {
            is RecipeDetailsEvent.NavigateInstructions -> navigateInstructions(event.recipe)
            is RecipeDetailsEvent.ShowError -> showSnackbar(event.message)
            is RecipeDetailsEvent.ShowRecipeSavedMessage -> showSnackbar(getString(R.string.fragment_home_recipe_saved))
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

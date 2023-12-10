package pl.smcebi.recipeme.ui.saved

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.ui.common.extensions.collectOnViewLifecycle
import pl.smcebi.recipeme.ui.common.extensions.notImplemented
import pl.smcebi.recipeme.ui.common.extensions.showSnackbar
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding
import pl.smcebi.recipeme.ui.saved.databinding.FragmentSavedRecipesBinding
import pl.smcebi.recipeme.uicommon.R.string.deep_link_recipe_details

@AndroidEntryPoint
internal class SavedRecipesFragment : Fragment(R.layout.fragment_saved_recipes) {

    private val binding by viewBinding(FragmentSavedRecipesBinding::bind)
    private val viewModel: SavedRecipesViewModel by viewModels()
    private var adapter: SavedRecipesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitTransition = MaterialFadeThrough()

        initViews()
        collectOnViewLifecycle(viewModel.state, ::onNewState)
        collectOnViewLifecycle(viewModel.event, ::onNewEvent)
    }

    private fun initViews() {
        with(binding) {
            adapter = SavedRecipesAdapter(
                onRecipeClick = { _, position -> viewModel.navigateDetails(position) },
                onBookmarkClick = viewModel::removeStoredRecipe
            )
            recipesRecyclerView.adapter = adapter
        }
    }

    private fun onNewState(state: SavedRecipesState) {
        with(binding) {
            adapter?.submitList(state.recipes)
            noRecipesTextView.isVisible = state.recipes.isEmpty()
        }
    }

    private fun onNewEvent(event: SavedRecipesEvent) {
        when (event) {
            is SavedRecipesEvent.NavigateDetails -> findNavController().navigate(
                getString(deep_link_recipe_details, event.recipeId).toUri(),
            )

            SavedRecipesEvent.ShowRecipeRemovedMessage -> showSnackbar(getString(R.string.fragment_saved_recipe_removed))
        }
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}
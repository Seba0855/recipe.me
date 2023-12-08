package pl.smcebi.recipeme.ui.home.main

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.search.SearchView.TransitionState.HIDDEN
import com.google.android.material.search.SearchView.TransitionState.HIDING
import com.google.android.material.search.SearchView.TransitionState.SHOWN
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.ui.common.extensions.collectOnViewLifecycle
import pl.smcebi.recipeme.ui.common.extensions.notImplemented
import pl.smcebi.recipeme.ui.common.extensions.onBackPressed
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener
import pl.smcebi.recipeme.ui.common.extensions.showSnackbar
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding
import pl.smcebi.recipeme.ui.home.R
import pl.smcebi.recipeme.ui.home.databinding.FragmentHomeBinding
import pl.smcebi.recipeme.ui.home.main.suggestions.SuggestionsAdapter

@AndroidEntryPoint
internal class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private var adapter: HomeAdapter? = null
    private var searchAdapter: SuggestionsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed {
            if (binding.searchView.currentTransitionState == SHOWN) {
                binding.searchView.hide()
            } else {
                requireActivity().moveTaskToBack(true)
            }
        }

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        exitTransition = MaterialElevationScale(false)
        reenterTransition = MaterialElevationScale(true)

        initViews()
        collectOnViewLifecycle(viewModel.state, ::onNewState)
        collectOnViewLifecycle(viewModel.event, ::onNewEvent)
    }

    private fun initViews() {
        with(binding) {
            adapter = HomeAdapter(
                onRecipeClick = ::navigateDetails,
                onBookmarkClick = viewModel::onBookmarkClick
            )
            searchAdapter = SuggestionsAdapter(
                onSuggestionClick = viewModel::onSuggestionClick
            )
            randomButton.setSafeOnClickListener {
                viewModel.onRandomClicked()
            }
            connectionError.tryAgainButton.setSafeOnClickListener {
                viewModel.tryAgain()
            }
            setupSearchView()

            recipesRecyclerView.adapter = adapter
            suggestionsRecyclerView.adapter = searchAdapter
        }
    }

    private fun onNewState(state: HomeViewState) {
        with(binding) {
            adapter?.submitList(state.recipes)
            searchAdapter?.submitList(state.searchSuggestions)
            initialSearchGroup.isVisible = state.showInitialMessage
            connectionError.root.isVisible = state.isError
            broccoliLoading.isVisible = state.inProgress
            notFoundGroup.isVisible = state.noSearchResult
        }
    }

    private fun onNewEvent(event: HomeViewEvent) {
        when (event) {
            is HomeViewEvent.NavigateDetails -> navigateDetailsById(event.recipeId)
            is HomeViewEvent.ShowError -> showSnackbar(event.message)
        }
    }

    private fun navigateDetailsById(recipeId: String) {
        findNavController().navigate(
            HomeFragmentDirections.navigateDetails(
                recipeId = recipeId,
                recipe = null
            )
        )
    }

    private fun navigateDetails(transitioningView: View, position: Int) {
        val recipe = viewModel.state.value.recipes[position]

        findNavController().navigate(
            directions = HomeFragmentDirections.navigateDetails(recipe, ""),
            navigatorExtras = FragmentNavigatorExtras(
                transitioningView to transitioningView.transitionName
            )
        )
    }

    private fun setupSearchView() {
        with(binding) {
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                viewModel.onSearchRequested(searchView.text.toString())
                false
            }

            searchView.editText.addTextChangedListener { text ->
                if (text.isNullOrBlank()) {
                    viewModel.clearSuggestions()
                }
            }

            searchView.addTransitionListener { _, previousState, newState ->
                when {
                    previousState == SHOWN && newState == HIDING -> viewModel.clearSuggestions()
                    newState == SHOWN -> viewModel.manageBottomNavVisibility(isVisible = false)
                    newState == HIDDEN -> viewModel.manageBottomNavVisibility(isVisible = true)
                }
            }
        }
    }

    override fun onDestroyView() {
        adapter = null
        searchAdapter = null
        super.onDestroyView()
    }
}

package pl.smcebi.recipeme.ui.home.main

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.ui.common.extensions.collectOnViewLifecycle
import pl.smcebi.recipeme.ui.common.extensions.notImplemented
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener
import pl.smcebi.recipeme.ui.common.extensions.showSnackbar
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding
import pl.smcebi.recipeme.ui.home.R
import pl.smcebi.recipeme.ui.home.databinding.FragmentHomeBinding

@AndroidEntryPoint
internal class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private var adapter: HomeAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            menuButton.setSafeOnClickListener {
                notImplemented()
            }
            randomButton.setSafeOnClickListener {
                viewModel.onRandomClicked()
            }
            connectionError.tryAgainButton.setSafeOnClickListener {
                viewModel.tryAgain()
            }
            recipesRecyclerView.adapter = adapter
        }
    }

    private fun onNewState(state: HomeViewState) {
        with(binding) {
            adapter?.submitList(state.recipes)
            connectionError.root.isVisible = state.isError
            broccoliLoading.isVisible = state.inProgress
        }
    }

    private fun onNewEvent(event: HomeViewEvent) {
        when (event) {
            is HomeViewEvent.ShowError -> showSnackbar(event.message)
        }
    }

    private fun navigateDetails(transitioningView: View, position: Int) {
        val recipe = viewModel.state.value.recipes[position]

        findNavController().navigate(
            directions = HomeFragmentDirections.navigateDetails(recipe),
            navigatorExtras = FragmentNavigatorExtras(
                transitioningView to transitioningView.transitionName
            )
        )
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}

package pl.smcebi.recipeme.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.R
import pl.smcebi.recipeme.databinding.FragmentHomeBinding
import pl.smcebi.recipeme.ui.common.extensions.collectOnViewLifecycle
import pl.smcebi.recipeme.ui.common.extensions.showSnackbar
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding

@AndroidEntryPoint
internal class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectOnViewLifecycle(viewModel.state, ::onNewState)
        collectOnViewLifecycle(viewModel.event, ::onNewEvent)
    }

    private fun onNewState(state: HomeViewState) {
        with(binding) {
            recipeTitle.text = state.title
        }
    }

    private fun onNewEvent(event: HomeViewEvent) {
        when (event) {
            is HomeViewEvent.ShowError -> showSnackbar(event.message)
        }
    }
}

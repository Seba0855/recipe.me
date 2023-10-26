package pl.smcebi.recipeme.ui.scanner.resolver

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.ui.common.extensions.collectOnViewLifecycle
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding
import pl.smcebi.recipeme.ui.scanner.R
import pl.smcebi.recipeme.ui.scanner.databinding.FragmentProductResolverBinding

@AndroidEntryPoint
internal class ProductResolverFragment : Fragment(R.layout.fragment_product_resolver) {

    private val viewModel: ProductResolverViewModel by viewModels()
    private val binding by viewBinding(FragmentProductResolverBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectOnViewLifecycle(viewModel.state, ::onNewState)
        collectOnViewLifecycle(viewModel.event, ::onNewEvent)
    }

    private fun onNewState(state: ProductResolverState) {

    }

    private fun onNewEvent(event: ProductResolverEvent) {

    }

    private fun startBarcodeCollection() {
        if (view != null) {
            viewModel.collectBarcodeData(viewLifecycleOwner)
            // get scanner fragment and start image analysis
        }
    }
}
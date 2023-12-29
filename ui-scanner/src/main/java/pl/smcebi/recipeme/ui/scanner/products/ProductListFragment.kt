package pl.smcebi.recipeme.ui.scanner.products

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.ui.common.extensions.collectOnViewLifecycle
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener
import pl.smcebi.recipeme.ui.common.extensions.showSnackbar
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding
import pl.smcebi.recipeme.ui.scanner.R
import pl.smcebi.recipeme.ui.scanner.databinding.FragmentProductListBinding

@AndroidEntryPoint
internal class ProductListFragment : Fragment(R.layout.fragment_product_list) {

    private val binding by viewBinding(FragmentProductListBinding::bind)
    private val viewModel: ProductListViewModel by viewModels()
    private var adapter: ProductsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        exitTransition = MaterialElevationScale(false).apply {
            duration = 200L
        }
        reenterTransition = MaterialElevationScale(true)

        initViews()
        collectOnViewLifecycle(viewModel.state, ::onNewState)
        collectOnViewLifecycle(viewModel.event, ::onNewEvent)
    }

    private fun initViews() {
        with(binding) {
            scanProductButton.setSafeOnClickListener {
                findNavController().navigate(ProductListFragmentDirections.navigateProductScanner())
            }
            adapter = ProductsAdapter(
                onDeleteClick = viewModel::removeStoredProduct
            )
            productsRecyclerView.adapter = adapter
        }
    }

    private fun onNewState(state: ProductListState) {
        adapter?.submitList(state.products)
        binding.noProductsTextView.isVisible = state.products.isEmpty()
    }

    private fun onNewEvent(event: ProductListEvent) {
        when (event) {
            ProductListEvent.ShowProductRemovedMessage -> showSnackbar(getString(R.string.fragment_products_product_removed))
            else -> {}
        }
    }
}
package pl.smcebi.recipeme.ui.scanner.resolver

import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.ui.common.extensions.collectOnViewLifecycle
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener
import pl.smcebi.recipeme.ui.common.extensions.showSnackbar
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding
import pl.smcebi.recipeme.ui.scanner.R
import pl.smcebi.recipeme.ui.scanner.camera.ScannerFragment
import pl.smcebi.recipeme.ui.scanner.databinding.FragmentProductResolverBinding

@AndroidEntryPoint
internal class ProductResolverFragment : Fragment(R.layout.fragment_product_resolver) {

    private val viewModel: ProductResolverViewModel by viewModels()
    private val binding by viewBinding(FragmentProductResolverBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        initViews()
        collectOnViewLifecycle(viewModel.state, ::onNewState)
        collectOnViewLifecycle(viewModel.event, ::onNewEvent)
    }

    override fun onResume() {
        super.onResume()
        startBarcodeCollection()
    }

    private fun initViews() {
        with(binding) {
            startBarcodeCollection()
            backButton.apply {
                clipToOutline = true
                setSafeOnClickListener {
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun onNewState(state: ProductResolverState) {
        with(binding) {
            progressIndicator.isVisible = state.inProgress
        }
    }

    private fun onNewEvent(event: ProductResolverEvent) {
        when (event) {
            ProductResolverEvent.ResumeImageAnalysis -> startBarcodeCollection()
            ProductResolverEvent.StopImageAnalysis -> stopBarcodeCollection()
            ProductResolverEvent.ShowProductSavedAnimation -> {
                with(binding.scannedProductCardView) {
                    addProductButton.isVisible = false
                    successIcon.playAnimation()
                    successIcon.addAnimatorUpdateListener { animation ->
                        animation.doOnEnd {
                            binding.scannedProductCardView.root.isVisible = false
                            successIcon.frame = 0
                        }
                    }
                    startBarcodeCollection()
                }
            }

            is ProductResolverEvent.ShowProduct -> with(binding) {
                scannedProductCardView.bind(
                    product = event.product,
                    onProductClicked = viewModel::saveProduct
                )
                scannedProductCardView.addProductButton.isVisible = true
                scannedProductCardView.root.isVisible = true
            }

            is ProductResolverEvent.DismissProduct -> {
                binding.scannedProductCardView.root.isVisible = false
                startBarcodeCollection()
            }

            is ProductResolverEvent.ShowError -> showSnackbar(
                event.message,
                ::startBarcodeCollection
            )
        }
    }

    private fun startBarcodeCollection() {
        if (view != null) {
            viewModel.collectBarcodeData(viewLifecycleOwner)
            binding.scannerFragment
                .getFragment<ScannerFragment>()
                .startImageAnalysis()
        }
    }

    private fun stopBarcodeCollection() {
        binding.scannerFragment
            .getFragment<ScannerFragment>()
            .stopImageAnalysis()
    }
}

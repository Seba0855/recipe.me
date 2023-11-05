package pl.smcebi.recipeme.ui.scanner.camera

import android.Manifest
import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Preview
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.ui.common.extensions.afterMeasured
import pl.smcebi.recipeme.ui.common.extensions.checkPermissionCompat
import pl.smcebi.recipeme.ui.common.extensions.openApplicationSettings
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener
import pl.smcebi.recipeme.ui.common.extensions.showSomethingWentWrong
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding
import pl.smcebi.recipeme.ui.scanner.R
import pl.smcebi.recipeme.ui.scanner.databinding.FragmentScannerBinding
import timber.log.Timber

@AndroidEntryPoint
internal class ScannerFragment : Fragment(R.layout.fragment_scanner) {

    private val viewModel: ScannerViewModel by viewModels()
    private val binding by viewBinding(FragmentScannerBinding::bind)
    private var preview: Preview? = null
    private var shouldAttachAnalysis = false

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        when {
            isGranted -> onCameraPermissionGranted()
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) ->
                onShowCameraPermissionRationale()

            else -> onCameraPermissionDenied()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preview = createPreview()
    }

    override fun onStart() {
        super.onStart()
        binding.permissionDeniedView.isVisible = false
        checkCameraPermission()
    }

    override fun onDestroyView() {
        preview = null
        super.onDestroyView()
    }

    fun startImageAnalysis() {
        if (hasCameraPermission()) {
            attachCameraAnalysis()
        } else {
            shouldAttachAnalysis = true
        }
    }

    fun stopImageAnalysis() {
        if (hasCameraPermission()) detachCameraAnalysis()
        shouldAttachAnalysis = false
    }

    private fun checkCameraPermission() {
        if (hasCameraPermission()) {
            onCameraPermissionGranted()
        } else {
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun onCameraPermissionGranted() {
        binding.permissionDeniedView.isVisible = false
        attachCameraPreview()
    }

    private fun attachCameraPreview() {
        requireContext().getCameraProvider { cameraProvider ->
            val preview = preview ?: return@getCameraProvider
            if (!cameraProvider.isBound(preview)) {
                val useCases =
                    if (shouldAttachAnalysis && !cameraProvider.isBound(viewModel.getImageAnalysis())) {
                        arrayOf(preview, viewModel.getImageAnalysis())
                    } else {
                        arrayOf(preview)
                    }

                try {
                    val camera = cameraProvider.bindToLifecycle(
                        viewLifecycleOwner,
                        viewModel.getCameraSelector(),
                        *useCases,
                    )
                    binding.previewView.afterMeasured {
                        camera.setAutofocusPoint()
                    }
                } catch (e: Exception) {
                    Timber.e(e, "Launching camera failed")
                }
                shouldAttachAnalysis = false
            }
        }
    }

    private fun attachCameraAnalysis() {
        requireContext().getCameraProvider { cameraProvider ->
            if (!cameraProvider.isBound(viewModel.getImageAnalysis())) {
                try {
                    cameraProvider.bindToLifecycle(
                        viewLifecycleOwner,
                        viewModel.getCameraSelector(),
                        viewModel.getImageAnalysis(),
                    )
                } catch (e: Exception) {
                    Timber.e(e, "Launching camera analysis failed")
                }
            }
        }
    }

    private fun detachCameraAnalysis() {
        requireContext().getCameraProvider { cameraProvider ->
            cameraProvider.unbind(viewModel.getImageAnalysis())
        }
    }

    private fun createPreview() =
        Preview.Builder()
            .build()
            .apply {
                setSurfaceProvider(binding.previewView.surfaceProvider)
            }

    private fun hasCameraPermission() =
        requireContext().checkPermissionCompat(Manifest.permission.CAMERA)

    private fun onShowCameraPermissionRationale() {
        with(binding) {
            permissionDeniedButton.setText(R.string.fragment_barcode_scanner_permission_rationale)
            permissionDeniedButton.setSafeOnClickListener {
                requestCameraPermission()
            }
            permissionDeniedView.isVisible = true
        }
    }

    private fun onCameraPermissionDenied() {
        with(binding) {
            permissionDeniedButton.setText(R.string.fragment_barcode_scanner_permission_denied)
            permissionDeniedButton.setSafeOnClickListener {
                try {
                    openApplicationSettings()
                } catch (e: ActivityNotFoundException) {
                    Timber.e(e, "Failed to open app system settings")
                    showSomethingWentWrong()
                }
            }
            permissionDeniedView.isVisible = true
        }
    }
}

package pl.smcebi.recipeme.ui.scanner.resolver

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import pl.smcebi.domain.products.GetProductByBarcodeUseCase
import pl.smcebi.recipeme.barcode.scanner.BarcodeData
import pl.smcebi.recipeme.barcode.scanner.local.BarcodeScannerMlKit
import pl.smcebi.recipeme.ui.common.extensions.EventsChannel
import pl.smcebi.recipeme.ui.common.extensions.mutate
import pl.smcebi.recipeme.ui.common.vibration.VibrationProvider
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
internal class ProductResolverViewModel @Inject constructor(
    private val getProductByBarcodeUseCase: GetProductByBarcodeUseCase,
    private val barcodeScanner: BarcodeScannerMlKit,
    private val vibratorUtil: VibrationProvider,
) : ViewModel() {

    private val mutableState = MutableStateFlow(ProductResolverState())
    val state: StateFlow<ProductResolverState> = mutableState.asStateFlow()

    private val mutableEvent = EventsChannel<ProductResolverEvent>()
    val event: Flow<ProductResolverEvent> = mutableEvent.receiveAsFlow()

    private var scannerJob: Job? = null

    fun collectBarcodeData(owner: LifecycleOwner) {
        if (scannerJob?.isActive == true) return
        scannerJob = owner.lifecycleScope.launch {

            // fetch latest scanner data
            val barcode = barcodeScanner.barcodesFlow.first()

            // prevent multiple launches on job started
            if (!isActive || state.value.inProgress) return@launch
            mutableEvent.send(ProductResolverEvent.StopImageAnalysis)
            // show scanning in progress

            vibratorUtil.vibrate(duration = VIBRATION_DURATION, amplitude = VIBRATION_AMPLITUDE)
            val barcodeResolverJob = launch { resolveBarcode(barcode) }

            delay(PROGRESS_DELAY)
            if (!isActive) return@launch
            if (!barcodeResolverJob.isCompleted) {
                mutableState.mutate {
                    copy(inProgress = true)
                }
                barcodeResolverJob.join()
                mutableState.mutate {
                    copy(inProgress = false)
                }
            }
        }
    }

    private suspend fun resolveBarcode(barcode: BarcodeData) {
        getProductByBarcodeUseCase(barcode.value)
            .onSuccess { productName ->
                // handle resolved product info
                viewModelScope.launch {
                    mutableEvent.send(ProductResolverEvent.ShowProduct(productName))
                    delay(5.seconds)
                    mutableEvent.send(ProductResolverEvent.DismissProduct)
                }
            }
            .onFailure { message ->
                // show error
                mutableEvent.send(ProductResolverEvent.ShowError(message))
            }
    }

    private companion object {
        const val VIBRATION_DURATION = 300L
        const val VIBRATION_AMPLITUDE = 100
        val PROGRESS_DELAY = 500.milliseconds
    }
}
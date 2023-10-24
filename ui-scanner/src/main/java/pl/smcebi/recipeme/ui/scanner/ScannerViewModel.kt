package pl.smcebi.recipeme.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.smcebi.recipeme.barcode.scanner.BarcodeData
import pl.smcebi.recipeme.barcode.scanner.CameraConfigProvider
import pl.smcebi.recipeme.barcode.scanner.local.BarcodeScannerMlKit
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class ScannerViewModel @Inject constructor(
    private val barcodeScanner: BarcodeScannerMlKit
) : ViewModel(), CameraConfigProvider by barcodeScanner {

    init {
        barcodeScanner.barcodesFlow
            .onEach(::handleBarcodeData)
            .launchIn(viewModelScope)
    }

    private fun handleBarcodeData(data: BarcodeData) {

        Timber.d("New data: ${data.value}")
    }
}

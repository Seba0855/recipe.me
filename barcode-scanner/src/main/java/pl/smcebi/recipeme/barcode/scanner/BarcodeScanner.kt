package pl.smcebi.recipeme.barcode.scanner

import kotlinx.coroutines.flow.Flow

interface BarcodeScanner : CameraConfigProvider {
    val barcodesFlow: Flow<BarcodeData>
}
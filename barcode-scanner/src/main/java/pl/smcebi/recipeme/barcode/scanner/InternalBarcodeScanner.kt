package pl.smcebi.recipeme.barcode.scanner

import kotlinx.coroutines.flow.Flow

interface InternalBarcodeScanner : CameraConfigProvider {
    val barcodesFlow: Flow<BarcodeData>
}
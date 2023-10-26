package pl.smcebi.recipeme.ui.scanner.camera

internal sealed class ScannerEvent {
    data class ShowScannedEan(val ean: String) : ScannerEvent()
}
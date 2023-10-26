package pl.smcebi.recipeme.barcode.scanner.local

import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.smcebi.recipeme.barcode.scanner.InternalBarcodeScanner

@Module
@InstallIn(SingletonComponent::class)
internal interface BarcodeScannerModule {

    @Binds
    fun bindInternalBarcodeScanner(impl: BarcodeScannerMlKit): InternalBarcodeScanner

    companion object {
        @Provides
        fun provideBarcodeScanning(): BarcodeScanner =
            BarcodeScanning.getClient(
                BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_EAN_13)
                    .build()
            )
    }
}
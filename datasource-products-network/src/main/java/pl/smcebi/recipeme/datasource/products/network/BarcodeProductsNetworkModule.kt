package pl.smcebi.recipeme.datasource.products.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.smcebi.recipeme.datasource.products.BarcodeProductsDataSource

@Module
@InstallIn(SingletonComponent::class)
internal interface BarcodeProductsNetworkModule {

    @Binds
    fun bindProductsDataSource(impl: BarcodeProductsNetworkDataSource): BarcodeProductsDataSource
}

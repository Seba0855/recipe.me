package pl.smcebi.recipeme.datasource.translation.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface TranslationNetworkModule {

    @Binds
    fun bindTranslationDataSource(impl: TranslationNetworkDataSource): TranslationNetworkDataSource
}

package pl.smcebi.recipeme.infrastructure.remote.common

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkCoroutinesModule {
    @Provides
    @NetworkCoroutineDispatcher
    fun provideNetworkCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

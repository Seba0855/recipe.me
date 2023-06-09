package pl.smcebi.recipeme.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import pl.smcebi.recipeme.di.annotations.NetworkCoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkCoroutinesModule {
    @Provides
    @NetworkCoroutineDispatcher
    fun provideNetworkCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

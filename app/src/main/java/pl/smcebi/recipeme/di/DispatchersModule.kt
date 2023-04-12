package pl.smcebi.recipeme.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import pl.smcebi.recipeme.di.DispatcherIO

@Module
@InstallIn(SingletonComponent::class)
internal object DispatchersModule {

    @Provides
    @DispatcherIO
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO
}

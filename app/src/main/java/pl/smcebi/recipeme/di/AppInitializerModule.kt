package pl.smcebi.recipeme.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.smcebi.recipeme.AppInitializersContainer

@Module
@InstallIn(SingletonComponent::class)
object AppInitializerModule {

    @Provides
    fun appInitializersContainer() = AppInitializersContainer()
}

package pl.smcebi.recipeme.infrastructure.remote.datasource.recipes

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import pl.smcebi.recipeme.di.annotations.Mock

@Module
@InstallIn(SingletonComponent::class)
internal interface RecipesNetworkDataSourceModule {

    @Binds
    fun bindDataSource(impl: RecipesNetworkDataSource): RecipesDataSource

    @Binds
    @Mock
    fun bindMockDataSource(impl: MockRecipesDataSource): RecipesDataSource
}

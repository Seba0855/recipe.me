package pl.smcebi.recipeme.recipes.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.smcebi.recipeme.datasource.common.Mock
import pl.smcebi.recipeme.recipes.RecipesDataSource

@Module
@InstallIn(SingletonComponent::class)
internal interface RecipesNetworkModule {

    @Binds
    fun bindDataSource(impl: RecipesNetworkDataSource): RecipesDataSource

    @Binds
    @Mock
    fun bindMockDataSource(impl: MockRecipesDataSource): RecipesDataSource
}

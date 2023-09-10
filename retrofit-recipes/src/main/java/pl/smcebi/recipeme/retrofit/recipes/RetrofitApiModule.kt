package pl.smcebi.recipeme.retrofit.recipes

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitApiModule {

    @Provides
    @Reusable
    fun provideRecipesApi(retrofit: Retrofit): RecipesApi = retrofit.create()
}

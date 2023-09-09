package pl.smcebi.recipeme.retrofit.base

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import pl.smcebi.recipeme.retrofit.base.interceptors.ApiKeyInterceptor

@Module
@InstallIn(SingletonComponent::class)
internal interface InterceptorsModule {

    @Binds
    @IntoSet
    fun bindApiKeyInterceptor(impl: ApiKeyInterceptor): Interceptor
}

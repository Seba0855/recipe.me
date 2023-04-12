package pl.smcebi.recipeme.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
internal object SerializationModule {

    @Provides
    @Reusable
    fun provideJsonSerialization(): Json =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
}

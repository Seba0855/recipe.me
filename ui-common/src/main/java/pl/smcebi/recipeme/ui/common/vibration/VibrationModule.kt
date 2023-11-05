package pl.smcebi.recipeme.ui.common.vibration

import android.content.Context
import android.os.Vibrator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object VibrationModule {

    @Provides
    fun provideVibratorService(@ApplicationContext context: Context): Vibrator =
        context.getSystemService(Vibrator::class.java)
}
package pl.smcebi.recipeme.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.smcebi.recipeme.database.Database
import pl.smcebi.recipeme.database.DatabaseConstants.DB_NAME
import pl.smcebi.recipeme.database.dao.RecipeDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context,
    ): Database = Room.databaseBuilder(
        context = applicationContext,
        klass = Database::class.java,
        name = DB_NAME
    ).build()

    @Provides
    @Reusable
    fun provideRecipesDao(database: Database): RecipeDao = database.recipeDao
}

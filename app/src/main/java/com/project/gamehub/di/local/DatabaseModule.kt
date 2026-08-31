package com.project.gamehub.di.local

import android.content.Context
import androidx.room3.Room
import com.project.gamehub.data.local.dao.SavedGamesDao
import com.project.gamehub.data.local.database.GameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): GameDatabase {
        return Room.databaseBuilder(
            context,
            GameDatabase::class.java,
            "games.db"
        ).build()
    }


    @Provides
    fun provideGameDao(
        database: GameDatabase
    ): SavedGamesDao {
        return database.gameDao()
    }
}
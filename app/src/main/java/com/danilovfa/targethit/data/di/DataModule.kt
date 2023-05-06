package com.danilovfa.targethit.data.di

import android.app.Application
import androidx.room.Room
import com.danilovfa.targethit.data.local.TargetHitDatabase
import com.danilovfa.targethit.data.local.dao.LeaderboardDao
import com.danilovfa.targethit.data.local.dao.LevelsDao
import com.danilovfa.targethit.data.repository.LeaderboardRepositoryImpl
import com.danilovfa.targethit.data.repository.LevelRepositoryImpl
import com.danilovfa.targethit.domain.repository.LeaderboardRepository
import com.danilovfa.targethit.domain.repository.LevelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideTargetHitDatabase(app: Application): TargetHitDatabase {
        return Room.databaseBuilder(
            app,
            TargetHitDatabase::class.java,
            TargetHitDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideLeaderboardDao(db: TargetHitDatabase) = db.leaderboardDao

    @Provides
    @Singleton
    fun provideLevelsDao(db: TargetHitDatabase) = db.levelsDao

    @Provides
    @Singleton
    fun provideLeaderboardRepository(leaderboardDao: LeaderboardDao): LeaderboardRepository {
        return LeaderboardRepositoryImpl(leaderboardDao)
    }

    @Provides
    @Singleton
    fun provideLevelRepository(levelsDao: LevelsDao): LevelRepository {
        return LevelRepositoryImpl(levelsDao)
    }

}
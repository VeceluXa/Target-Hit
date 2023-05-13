package com.danilovfa.targethit.domain.di

import com.danilovfa.targethit.domain.repository.LeaderboardRepository
import com.danilovfa.targethit.domain.repository.LevelRepository
import com.danilovfa.targethit.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideGetLeaderboardUseCase(leaderboardRepository: LeaderboardRepository): GetLeaderboardUseCase {
        return GetLeaderboardUseCase(leaderboardRepository)
    }

    @Provides
    fun provideGetLevelByIdUseCase(levelRepository: LevelRepository): GetLevelByIdUseCase {
        return GetLevelByIdUseCase(levelRepository)
    }

    @Provides
    fun provideGetLevelsUseCase(levelRepository: LevelRepository): GetLevelsUseCase {
        return GetLevelsUseCase(levelRepository)
    }

    @Provides
    fun provideSetLevelCompletedByIdUseCase(levelRepository: LevelRepository): SetLevelCompletedByIdUseCase {
        return SetLevelCompletedByIdUseCase(levelRepository)
    }

    @Provides
    fun provideSetScoreUseCase(leaderboardRepository: LeaderboardRepository): SetScoreUseCase {
        return SetScoreUseCase(leaderboardRepository)
    }
}
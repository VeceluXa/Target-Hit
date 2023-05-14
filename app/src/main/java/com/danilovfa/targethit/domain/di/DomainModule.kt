package com.danilovfa.targethit.domain.di

import com.danilovfa.targethit.domain.repository.LeaderboardRepository
import com.danilovfa.targethit.domain.repository.LevelRepository
import com.danilovfa.targethit.domain.time.*
import com.danilovfa.targethit.domain.time.state.StopwatchStateCalculator
import com.danilovfa.targethit.domain.time.state.StopwatchStateHolder
import com.danilovfa.targethit.domain.time.timestamp.DateTimestampProvider
import com.danilovfa.targethit.domain.time.timestamp.ElapsedTimeCalculator
import com.danilovfa.targethit.domain.time.timestamp.TimestampProvider
import com.danilovfa.targethit.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

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
    @Provides
    fun provideStartStopwatchUseCase(stopwatchListOrchestrator: StopwatchListOrchestrator): StartStopwatchUseCase {
        return StartStopwatchUseCase(stopwatchListOrchestrator)
    }

    @Provides
    fun providePauseStopwatchUseCase(stopwatchListOrchestrator: StopwatchListOrchestrator): PauseStopWatchUseCase {
        return PauseStopWatchUseCase(stopwatchListOrchestrator)
    }

    @Provides
//    @Singleton
    fun provideTimeStampProvider(): TimestampProvider {
        return DateTimestampProvider()
    }

    @Provides
//    @Singleton
    fun provideElapsedTimeCalculator(timestampProvider: TimestampProvider): ElapsedTimeCalculator {
        return ElapsedTimeCalculator(timestampProvider)
    }
    @Provides
//    @Singleton
    fun provideStopWatchStateCalculator(
        timestampProvider: TimestampProvider,
        elapsedTimeCalculator: ElapsedTimeCalculator
    ): StopwatchStateCalculator {
        return StopwatchStateCalculator(timestampProvider, elapsedTimeCalculator)
    }
    @Provides
//    @Singleton
    fun provideStopWatchStateHolder(
        stopwatchStateCalculator: StopwatchStateCalculator,
        elapsedTimeCalculator: ElapsedTimeCalculator
    ): StopwatchStateHolder {
        return StopwatchStateHolder(stopwatchStateCalculator, elapsedTimeCalculator)
    }

    @Provides
//    @Singleton
    fun provideStopWatchListOrchestrator(
        stopwatchStateHolder: StopwatchStateHolder
    ): StopwatchListOrchestrator {
        return StopwatchListOrchestrator(
            stopwatchStateHolder,
            scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
        )
    }
}
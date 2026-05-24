package com.infinitesudoku.app

import com.infinitesudoku.feature.game.GameReducer
import com.infinitesudoku.feature.game.GameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Core application module providing app-wide dependencies.
 */
val appModule = module {
    // Core dependencies will be added here
}

/**
 * Puzzle engine module providing solver and generator.
 */
val engineModule = module {
    // PuzzleSolver, PuzzleGenerator, PuzzleValidator will be added here
}

/**
 * Game feature module providing game-related ViewModels.
 */
val gameModule = module {
    single { GameReducer() }
    viewModel { GameViewModel(get()) }
}

/**
 * All Koin modules combined.
 */
val allModules = listOf(
    appModule,
    engineModule,
    gameModule
)

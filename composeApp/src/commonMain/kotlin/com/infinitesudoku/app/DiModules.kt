package com.infinitesudoku.app

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
    // GameViewModel will be added here
}

/**
 * All Koin modules combined.
 */
val allModules = listOf(
    appModule,
    engineModule,
    gameModule
)

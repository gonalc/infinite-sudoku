package com.infinitesudoku.feature.game

import com.infinitesudoku.core.domain.CellIndex
import com.infinitesudoku.core.domain.Puzzle

sealed interface GameIntent {
    // Cell interaction
    data class SelectCell(val index: CellIndex) : GameIntent
    data class PlaceNumber(val number: Int) : GameIntent
    data class TogglePencilMark(val number: Int) : GameIntent
    data object ClearCell : GameIntent

    // Mode toggles
    data object TogglePencilMode : GameIntent
    data object ToggleInputMode : GameIntent

    // History
    data object Undo : GameIntent
    data object Redo : GameIntent

    // Timer
    data object Pause : GameIntent
    data object Resume : GameIntent
    data object ToggleTimerVisibility : GameIntent
    data object TimerTick : GameIntent

    // Highlighting
    data class SetHighlightedNumber(val number: Int?) : GameIntent

    // Completion
    data object DismissCompletionDialog : GameIntent

    // Game lifecycle
    data class LoadPuzzle(val puzzle: Puzzle) : GameIntent
}

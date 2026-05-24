package com.infinitesudoku.feature.game

import com.infinitesudoku.core.domain.Cell
import com.infinitesudoku.core.domain.CellIndex
import com.infinitesudoku.core.domain.Puzzle
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

enum class InputMode {
    CELL_FIRST,     // Select cell, then number (default)
    NUMBER_FIRST    // Select number, then cells
}

data class GameState(
    // Puzzle data
    val puzzle: Puzzle?,
    val currentBoard: PersistentList<Cell>,

    // Selection & interaction
    val selectedCell: CellIndex?,
    val highlightedNumber: Int?,

    // Input modes
    val pencilMode: Boolean,
    val inputMode: InputMode,

    // History
    val undoStack: PersistentList<Move>,
    val redoStack: PersistentList<Move>,

    // Timer
    val elapsedSeconds: Long,
    val isPaused: Boolean,
    val timerVisible: Boolean,

    // Game state
    val isCompleted: Boolean,
    val showCompletionDialog: Boolean,

    // Settings
    val validationEnabled: Boolean,
    val autoClearPencilMarks: Boolean
) {
    companion object {
        val Initial = GameState(
            puzzle = null,
            currentBoard = persistentListOf(),
            selectedCell = null,
            highlightedNumber = null,
            pencilMode = false,
            inputMode = InputMode.CELL_FIRST,
            undoStack = persistentListOf(),
            redoStack = persistentListOf(),
            elapsedSeconds = 0L,
            isPaused = false,
            timerVisible = true,
            isCompleted = false,
            showCompletionDialog = false,
            validationEnabled = true,
            autoClearPencilMarks = true
        )
    }
}

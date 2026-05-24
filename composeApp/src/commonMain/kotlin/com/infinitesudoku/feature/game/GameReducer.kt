package com.infinitesudoku.feature.game

import com.infinitesudoku.core.domain.Cell
import com.infinitesudoku.core.domain.CellIndex
import com.infinitesudoku.core.domain.Puzzle
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

class GameReducer {

    fun reduce(state: GameState, intent: GameIntent): GameState {
        return when (intent) {
            is GameIntent.SelectCell -> handleSelectCell(state, intent.index)
            is GameIntent.PlaceNumber -> handlePlaceNumber(state, intent.number)
            is GameIntent.TogglePencilMark -> handleTogglePencilMark(state, intent.number)
            is GameIntent.ClearCell -> handleClearCell(state)
            is GameIntent.TogglePencilMode -> state.copy(pencilMode = !state.pencilMode)
            is GameIntent.ToggleInputMode -> state.copy(
                inputMode = if (state.inputMode == InputMode.CELL_FIRST)
                    InputMode.NUMBER_FIRST else InputMode.CELL_FIRST
            )
            is GameIntent.Undo -> handleUndo(state)
            is GameIntent.Redo -> handleRedo(state)
            is GameIntent.TimerTick -> state.copy(elapsedSeconds = state.elapsedSeconds + 1)
            is GameIntent.Pause -> state.copy(isPaused = true)
            is GameIntent.Resume -> state.copy(isPaused = false)
            is GameIntent.ToggleTimerVisibility -> state.copy(timerVisible = !state.timerVisible)
            is GameIntent.SetHighlightedNumber -> state.copy(highlightedNumber = intent.number)
            is GameIntent.DismissCompletionDialog -> state.copy(showCompletionDialog = false)
            is GameIntent.LoadPuzzle -> handleLoadPuzzle(state, intent.puzzle)
        }
    }

    private fun handleSelectCell(state: GameState, index: CellIndex): GameState {
        if (state.currentBoard.isEmpty()) return state

        val cell = state.currentBoard[index.value]
        val newSelectedCell = if (state.selectedCell == index) null else index
        val newHighlightedNumber = newSelectedCell?.let {
            cell.value
        }

        return state.copy(
            selectedCell = newSelectedCell,
            highlightedNumber = newHighlightedNumber
        )
    }

    private fun handlePlaceNumber(state: GameState, number: Int): GameState {
        val selectedIndex = state.selectedCell ?: return state
        val cell = state.currentBoard[selectedIndex.value]

        // Cannot modify given cells
        if (cell.isGiven) return state

        // If in pencil mode, toggle pencil mark instead
        if (state.pencilMode) {
            return handleTogglePencilMark(state, number)
        }

        // Create move for undo stack
        val move = Move(
            cellIndex = selectedIndex,
            oldValue = cell.value,
            newValue = number,
            oldPencilMarks = cell.pencilMarks,
            newPencilMarks = persistentSetOf()
        )

        // Update cell
        val newCell = cell.copy(
            value = number,
            pencilMarks = persistentSetOf(),
            isError = if (state.validationEnabled) {
                state.puzzle?.board?.solution?.get(selectedIndex.value) != number
            } else false
        )

        var newBoard = state.currentBoard.set(selectedIndex.value, newCell)

        // Auto-clear pencil marks in same row/col/box if enabled
        if (state.autoClearPencilMarks) {
            newBoard = clearPencilMarksInRelatedCells(newBoard, selectedIndex, number)
        }

        // Check completion
        val isComplete = checkCompletion(newBoard, state.puzzle?.board?.solution)

        return state.copy(
            currentBoard = newBoard,
            undoStack = state.undoStack.add(move),
            redoStack = persistentListOf(), // Clear redo on new action
            isCompleted = isComplete,
            showCompletionDialog = isComplete,
            highlightedNumber = number
        )
    }

    private fun handleTogglePencilMark(state: GameState, number: Int): GameState {
        val selectedIndex = state.selectedCell ?: return state
        val cell = state.currentBoard[selectedIndex.value]

        // Cannot add pencil marks to given cells or filled cells
        if (cell.isGiven || cell.value != null) return state

        val newPencilMarks = if (number in cell.pencilMarks) {
            cell.pencilMarks.remove(number)
        } else {
            cell.pencilMarks.add(number)
        }

        // Create move for undo stack
        val move = Move(
            cellIndex = selectedIndex,
            oldValue = cell.value,
            newValue = cell.value,
            oldPencilMarks = cell.pencilMarks,
            newPencilMarks = newPencilMarks
        )

        val newCell = cell.copy(pencilMarks = newPencilMarks)
        val newBoard = state.currentBoard.set(selectedIndex.value, newCell)

        return state.copy(
            currentBoard = newBoard,
            undoStack = state.undoStack.add(move),
            redoStack = persistentListOf() // Clear redo on new action
        )
    }

    private fun handleClearCell(state: GameState): GameState {
        val selectedIndex = state.selectedCell ?: return state
        val cell = state.currentBoard[selectedIndex.value]

        // Cannot clear given cells
        if (cell.isGiven) return state

        // Nothing to clear
        if (cell.value == null && cell.pencilMarks.isEmpty()) return state

        // Create move for undo stack
        val move = Move(
            cellIndex = selectedIndex,
            oldValue = cell.value,
            newValue = null,
            oldPencilMarks = cell.pencilMarks,
            newPencilMarks = persistentSetOf()
        )

        val newCell = cell.copy(
            value = null,
            pencilMarks = persistentSetOf(),
            isError = false
        )

        val newBoard = state.currentBoard.set(selectedIndex.value, newCell)

        return state.copy(
            currentBoard = newBoard,
            undoStack = state.undoStack.add(move),
            redoStack = persistentListOf(), // Clear redo on new action
            isCompleted = false,
            showCompletionDialog = false
        )
    }

    private fun clearPencilMarksInRelatedCells(
        board: PersistentList<Cell>,
        index: CellIndex,
        number: Int
    ): PersistentList<Cell> {
        var result = board
        board.forEachIndexed { i, cell ->
            val cellIndex = CellIndex(i)
            if (cellIndex.row == index.row ||
                cellIndex.col == index.col ||
                cellIndex.box == index.box
            ) {
                if (number in cell.pencilMarks) {
                    result = result.set(i, cell.copy(
                        pencilMarks = cell.pencilMarks.remove(number)
                    ))
                }
            }
        }
        return result
    }

    private fun handleUndo(state: GameState): GameState {
        if (state.undoStack.isEmpty()) return state

        val move = state.undoStack.last()
        val cell = state.currentBoard[move.cellIndex.value]

        val restoredCell = cell.copy(
            value = move.oldValue,
            pencilMarks = move.oldPencilMarks,
            isError = false
        )

        return state.copy(
            currentBoard = state.currentBoard.set(move.cellIndex.value, restoredCell),
            undoStack = state.undoStack.removeAt(state.undoStack.lastIndex),
            redoStack = state.redoStack.add(move),
            isCompleted = false,
            showCompletionDialog = false
        )
    }

    private fun handleRedo(state: GameState): GameState {
        if (state.redoStack.isEmpty()) return state

        val move = state.redoStack.last()
        val cell = state.currentBoard[move.cellIndex.value]

        val newCell = cell.copy(
            value = move.newValue,
            pencilMarks = move.newPencilMarks,
            isError = if (state.validationEnabled) {
                state.puzzle?.board?.solution?.get(move.cellIndex.value) != move.newValue
            } else false
        )

        val newBoard = state.currentBoard.set(move.cellIndex.value, newCell)
        val isComplete = checkCompletion(newBoard, state.puzzle?.board?.solution)

        return state.copy(
            currentBoard = newBoard,
            undoStack = state.undoStack.add(move),
            redoStack = state.redoStack.removeAt(state.redoStack.lastIndex),
            isCompleted = isComplete,
            showCompletionDialog = isComplete
        )
    }

    private fun checkCompletion(board: PersistentList<Cell>, solution: PersistentList<Int>?): Boolean {
        if (solution == null) return false
        return board.withIndex().all { (index, cell) ->
            cell.value == solution[index]
        }
    }

    private fun handleLoadPuzzle(state: GameState, puzzle: Puzzle): GameState {
        return state.copy(
            puzzle = puzzle,
            currentBoard = puzzle.board.cells,
            selectedCell = null,
            highlightedNumber = null,
            undoStack = persistentListOf(),
            redoStack = persistentListOf(),
            elapsedSeconds = 0L,
            isCompleted = false,
            showCompletionDialog = false
        )
    }
}

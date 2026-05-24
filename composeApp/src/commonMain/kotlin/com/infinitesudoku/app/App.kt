package com.infinitesudoku.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.infinitesudoku.core.domain.Cell
import com.infinitesudoku.core.domain.CellIndex
import com.infinitesudoku.ui.components.SudokuBoard
import com.infinitesudoku.ui.theme.InfiniteSudokuTheme
import kotlinx.collections.immutable.persistentSetOf

@Composable
fun App() {
    InfiniteSudokuTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                // Test puzzle setup
                var selectedCell by remember { mutableStateOf<CellIndex?>(null) }
                val highlightedNumber by remember {
                    derivedStateOf {
                        selectedCell?.let { testCells[it.value].value }
                    }
                }

                SudokuBoard(
                    cells = testCells,
                    selectedCell = selectedCell,
                    highlightedNumber = highlightedNumber,
                    onCellClick = { selectedCell = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }
}

// Test data: A simple Sudoku puzzle for visual verification
private val testCells = buildList {
    // Create 81 cells
    repeat(81) { i ->
        val index = CellIndex(i)
        // Add some given numbers in a valid Sudoku pattern
        val value = when (i) {
            // First row
            0 -> 5; 2 -> 3; 5 -> 7; 6 -> 1; 8 -> 6
            // Second row
            10 -> 1; 11 -> 9; 12 -> 5; 15 -> 3
            // Third row
            19 -> 8; 23 -> 6; 26 -> 4
            // Fourth row
            28 -> 8; 31 -> 6; 34 -> 3
            // Fifth row
            36 -> 4; 37 -> 5; 39 -> 8; 41 -> 3; 43 -> 1; 44 -> 7
            // Sixth row
            46 -> 7; 49 -> 2; 52 -> 6
            // Seventh row
            54 -> 9; 58 -> 6; 62 -> 5
            // Eighth row
            65 -> 4; 68 -> 2; 69 -> 6; 70 -> 8
            // Ninth row
            72 -> 5; 74 -> 1; 76 -> 7; 78 -> 9; 80 -> 3
            else -> null
        }

        // Add some user-entered values (not given)
        val userValue = when (i) {
            1 -> 2  // User entered 2
            9 -> 6  // User entered 6
            else -> null
        }

        // Add pencil marks to some empty cells
        val pencilMarks = when (i) {
            3 -> persistentSetOf(2, 4, 9)
            4 -> persistentSetOf(2, 4, 7, 9)
            7 -> persistentSetOf(2, 8, 9)
            else -> persistentSetOf()
        }

        // Add an error cell for testing
        val isError = i == 1 // Mark the user's entry as error for visual test

        add(
            Cell(
                index = index,
                value = value ?: userValue,
                isGiven = value != null,
                pencilMarks = if (value == null && userValue == null) pencilMarks else persistentSetOf(),
                isError = isError
            )
        )
    }
}

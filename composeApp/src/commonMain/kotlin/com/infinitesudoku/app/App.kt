package com.infinitesudoku.app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.infinitesudoku.core.domain.CellIndex
import com.infinitesudoku.core.util.TestPuzzles
import com.infinitesudoku.ui.components.SudokuBoard
import com.infinitesudoku.ui.theme.InfiniteSudokuTheme

@Composable
fun App() {
    InfiniteSudokuTheme {
        // For testing: use local state before ViewModel is ready
        val puzzle = remember { TestPuzzles.createEasyPuzzle() }
        var selectedCell by remember { mutableStateOf<CellIndex?>(null) }
        var highlightedNumber by remember { mutableStateOf<Int?>(null) }

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Text(
                    text = "Infinite Sudoku",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )

                Text(
                    text = "Difficulty: ${puzzle.board.difficulty}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Sudoku Board
                SudokuBoard(
                    cells = puzzle.board.cells.toList(),
                    selectedCell = selectedCell,
                    highlightedNumber = highlightedNumber,
                    onCellClick = { index ->
                        selectedCell = if (selectedCell == index) null else index
                        highlightedNumber = puzzle.board.cells[index.value].value
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Debug info
                Text(
                    text = "Selected: ${selectedCell?.value ?: "None"}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

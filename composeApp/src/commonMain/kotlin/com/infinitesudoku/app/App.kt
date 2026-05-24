package com.infinitesudoku.app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.infinitesudoku.core.util.TestPuzzles
import com.infinitesudoku.feature.game.GameIntent
import com.infinitesudoku.feature.game.GameViewModel
import com.infinitesudoku.ui.components.SudokuBoard
import com.infinitesudoku.ui.theme.InfiniteSudokuTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    InfiniteSudokuTheme {
        val viewModel: GameViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        // Load test puzzle on first composition
        LaunchedEffect(Unit) {
            val puzzle = TestPuzzles.createEasyPuzzle()
            viewModel.dispatch(GameIntent.LoadPuzzle(puzzle))
        }

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

                state.puzzle?.let { puzzle ->
                    Text(
                        text = "Difficulty: ${puzzle.board.difficulty}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sudoku Board
                SudokuBoard(
                    cells = state.currentBoard.toList(),
                    selectedCell = state.selectedCell,
                    highlightedNumber = state.highlightedNumber,
                    onCellClick = { index ->
                        viewModel.dispatch(GameIntent.SelectCell(index))
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Debug info
                Text(
                    text = "Selected: ${state.selectedCell?.value ?: "None"}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

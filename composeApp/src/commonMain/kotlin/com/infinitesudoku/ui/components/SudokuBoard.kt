package com.infinitesudoku.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.infinitesudoku.core.domain.Cell
import com.infinitesudoku.core.domain.CellIndex

/**
 * Main Sudoku board component that displays a 9x9 grid with proper visual hierarchy.
 *
 * @param cells List of 81 cells representing the board state
 * @param selectedCell Currently selected cell index
 * @param highlightedNumber Number to highlight across the board (when user selects a filled cell)
 * @param onCellClick Callback when a cell is tapped
 * @param modifier Modifier to apply to the board
 */
@Composable
fun SudokuBoard(
    cells: List<Cell>,
    selectedCell: CellIndex?,
    highlightedNumber: Int?,
    onCellClick: (CellIndex) -> Unit,
    modifier: Modifier = Modifier
) {
    // Use BoxWithConstraints to make the board square
    BoxWithConstraints(modifier = modifier.aspectRatio(1f)) {
        val cellSize = maxWidth / 9

        // Draw grid lines using Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawGrid(cellSize.toPx())
        }

        // Grid of cells
        Column {
            repeat(9) { row ->
                Row {
                    repeat(9) { col ->
                        val index = CellIndex.fromRowCol(row, col)
                        val cell = cells[index.value]
                        SudokuCell(
                            cell = cell,
                            isSelected = selectedCell == index,
                            isHighlighted = highlightedNumber != null && cell.value == highlightedNumber,
                            isSameRowColBox = selectedCell?.let {
                                it.row == row || it.col == col || it.box == index.box
                            } ?: false,
                            onClick = { onCellClick(index) },
                            modifier = Modifier.size(cellSize)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Individual Sudoku cell with different visual states.
 */
@Composable
private fun SudokuCell(
    cell: Cell,
    isSelected: Boolean,
    isHighlighted: Boolean,
    isSameRowColBox: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        isHighlighted -> MaterialTheme.colorScheme.secondaryContainer
        isSameRowColBox -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.surface
    }

    val textColor = when {
        cell.isError -> MaterialTheme.colorScheme.error
        cell.isGiven -> MaterialTheme.colorScheme.onSurface
        else -> MaterialTheme.colorScheme.primary
    }

    Box(
        modifier = modifier
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (cell.value != null) {
            Text(
                text = cell.value.toString(),
                style = MaterialTheme.typography.headlineMedium,
                color = textColor,
                fontWeight = if (cell.isGiven) FontWeight.Bold else FontWeight.Normal
            )
        } else if (cell.pencilMarks.isNotEmpty()) {
            PencilMarksGrid(pencilMarks = cell.pencilMarks)
        }
    }
}

/**
 * 3x3 grid of candidate numbers (pencil marks) for empty cells.
 */
@Composable
private fun PencilMarksGrid(
    pencilMarks: Set<Int>,
    modifier: Modifier = Modifier
) {
    // 3x3 grid of small numbers
    Column(
        modifier = modifier.fillMaxSize().padding(2.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(3) { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(3) { col ->
                    val number = row * 3 + col + 1
                    Text(
                        text = if (number in pencilMarks) number.toString() else "",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Draws the Sudoku grid lines with thick borders for 3x3 boxes.
 */
private fun DrawScope.drawGrid(cellSize: Float) {
    val thinStroke = 1.dp.toPx()
    val thickStroke = 3.dp.toPx()

    // Draw all lines
    for (i in 0..9) {
        val strokeWidth = if (i % 3 == 0) thickStroke else thinStroke
        val color = if (i % 3 == 0) Color.Black else Color.Gray

        // Vertical lines
        drawLine(
            color = color,
            start = Offset(i * cellSize, 0f),
            end = Offset(i * cellSize, 9 * cellSize),
            strokeWidth = strokeWidth
        )

        // Horizontal lines
        drawLine(
            color = color,
            start = Offset(0f, i * cellSize),
            end = Offset(9 * cellSize, i * cellSize),
            strokeWidth = strokeWidth
        )
    }
}

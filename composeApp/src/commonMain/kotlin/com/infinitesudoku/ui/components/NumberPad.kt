package com.infinitesudoku.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.infinitesudoku.core.domain.Cell
import kotlinx.collections.immutable.PersistentList

/**
 * Number input pad with buttons 1-9.
 * Shows visual feedback for selected numbers and completion status.
 */
@Composable
fun NumberPad(
    onNumberClick: (Int) -> Unit,
    selectedNumber: Int?,
    numberCounts: Map<Int, Int>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        (1..9).forEach { number ->
            NumberButton(
                number = number,
                isSelected = selectedNumber == number,
                isComplete = (numberCounts[number] ?: 0) >= 9,
                onClick = { onNumberClick(number) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Individual number button in the input pad.
 */
@Composable
private fun NumberButton(
    number: Int,
    isSelected: Boolean,
    isComplete: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primary
        isComplete -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.surface
    }

    val textColor = when {
        isSelected -> MaterialTheme.colorScheme.onPrimary
        isComplete -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable(enabled = !isComplete, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.headlineMedium,
            color = textColor
        )
    }
}

/**
 * Action buttons row with undo, redo, pencil mode, and delete.
 */
@Composable
fun ActionButtonsRow(
    onUndoClick: () -> Unit,
    onRedoClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onPencilToggle: () -> Unit,
    pencilMode: Boolean,
    canUndo: Boolean,
    canRedo: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButton(
            text = "Undo",
            enabled = canUndo,
            onClick = onUndoClick
        )
        ActionButton(
            text = "Redo",
            enabled = canRedo,
            onClick = onRedoClick
        )
        ActionButton(
            text = "Pencil",
            isToggled = pencilMode,
            onClick = onPencilToggle
        )
        ActionButton(
            text = "Clear",
            onClick = onDeleteClick
        )
    }
}

/**
 * Individual action button with text label.
 */
@Composable
private fun ActionButton(
    text: String,
    enabled: Boolean = true,
    isToggled: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isToggled -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surface
    }

    val textColor = when {
        !enabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        isToggled -> MaterialTheme.colorScheme.onPrimaryContainer
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = textColor
        )
    }
}

/**
 * Calculate how many of each number (1-9) are currently placed on the board.
 * Used to determine which numbers are complete (all 9 placed).
 */
fun calculateNumberCounts(board: PersistentList<Cell>): Map<Int, Int> {
    val counts = mutableMapOf<Int, Int>()
    board.forEach { cell ->
        cell.value?.let { value ->
            counts[value] = (counts[value] ?: 0) + 1
        }
    }
    return counts
}

package com.infinitesudoku.core.domain

import kotlinx.collections.immutable.PersistentSet

data class Cell(
    val index: CellIndex,
    val value: Int?,                      // null = empty, 1-9 = filled
    val isGiven: Boolean,                 // true = original clue (immutable)
    val pencilMarks: PersistentSet<Int>,  // candidate numbers 1-9
    val isError: Boolean                  // conflict detected
) {
    val isEmpty: Boolean get() = value == null
    val isFilled: Boolean get() = value != null
}

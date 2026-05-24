package com.infinitesudoku.feature.game

import com.infinitesudoku.core.domain.CellIndex
import kotlinx.collections.immutable.PersistentSet

data class Move(
    val cellIndex: CellIndex,
    val oldValue: Int?,
    val newValue: Int?,
    val oldPencilMarks: PersistentSet<Int>,
    val newPencilMarks: PersistentSet<Int>
)

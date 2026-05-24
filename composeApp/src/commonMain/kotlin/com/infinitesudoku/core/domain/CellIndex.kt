package com.infinitesudoku.core.domain

@JvmInline
value class CellIndex(val value: Int) {
    init {
        require(value in 0..80) { "Cell index must be 0-80" }
    }

    val row: Int get() = value / 9
    val col: Int get() = value % 9
    val box: Int get() = (row / 3) * 3 + (col / 3)

    companion object {
        fun fromRowCol(row: Int, col: Int): CellIndex =
            CellIndex(row * 9 + col)
    }
}

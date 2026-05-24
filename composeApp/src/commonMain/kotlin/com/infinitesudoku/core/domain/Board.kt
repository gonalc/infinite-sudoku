package com.infinitesudoku.core.domain

import kotlinx.collections.immutable.PersistentList

data class Board(
    val cells: PersistentList<Cell>,      // 81 cells, indexed 0-80
    val difficulty: Difficulty,
    val solution: PersistentList<Int>     // correct values for validation
) {
    init {
        require(cells.size == 81) { "Board must have 81 cells" }
        require(solution.size == 81) { "Solution must have 81 values" }
    }

    operator fun get(index: CellIndex): Cell = cells[index.value]

    fun getRow(row: Int): List<Cell> = cells.filter { it.index.row == row }
    fun getColumn(col: Int): List<Cell> = cells.filter { it.index.col == col }
    fun getBox(box: Int): List<Cell> = cells.filter { it.index.box == box }

    fun withCell(index: CellIndex, cell: Cell): Board =
        copy(cells = cells.set(index.value, cell))

    fun isComplete(): Boolean =
        cells.all { it.value == solution[it.index.value] }
}

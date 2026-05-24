package com.infinitesudoku.core.util

import com.infinitesudoku.core.domain.*
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.Clock

/**
 * Test puzzles for development and testing.
 * Format: 81-character string where '0' = empty cell, '1'-'9' = given clue
 */
object TestPuzzles {

    // Easy puzzle - many clues
    private const val EASY_CLUES =
        "530070000" +
        "600195000" +
        "098000060" +
        "800060003" +
        "400803001" +
        "700020006" +
        "060000280" +
        "000419005" +
        "000080079"

    private const val EASY_SOLUTION =
        "534678912" +
        "672195348" +
        "198342567" +
        "859761423" +
        "426853791" +
        "713924856" +
        "961537284" +
        "287419635" +
        "345286179"

    fun createEasyPuzzle(): Puzzle {
        return createPuzzle(
            id = 1L,
            clues = EASY_CLUES,
            solution = EASY_SOLUTION,
            difficulty = Difficulty.EASY
        )
    }

    // Medium puzzle
    private const val MEDIUM_CLUES =
        "020000000" +
        "000600003" +
        "074080000" +
        "000003002" +
        "080040010" +
        "600500000" +
        "000010780" +
        "500009000" +
        "000000040"

    private const val MEDIUM_SOLUTION =
        "126437958" +
        "895612473" +
        "374985126" +
        "457193862" +
        "983246517" +
        "612578394" +
        "249351786" +
        "568729431" +
        "731864249"

    fun createMediumPuzzle(): Puzzle {
        return createPuzzle(
            id = 2L,
            clues = MEDIUM_CLUES,
            solution = MEDIUM_SOLUTION,
            difficulty = Difficulty.MEDIUM
        )
    }

    private fun createPuzzle(
        id: Long,
        clues: String,
        solution: String,
        difficulty: Difficulty,
        isDaily: Boolean = false
    ): Puzzle {
        require(clues.length == 81) { "Clues must be 81 characters" }
        require(solution.length == 81) { "Solution must be 81 characters" }

        val solutionList = solution.map { it.digitToInt() }.toPersistentList()

        val cells = clues.mapIndexed { index, char ->
            val value = if (char == '0') null else char.digitToInt()
            Cell(
                index = CellIndex(index),
                value = value,
                isGiven = value != null,
                pencilMarks = persistentSetOf(),
                isError = false
            )
        }.toPersistentList()

        val board = Board(
            cells = cells,
            difficulty = difficulty,
            solution = solutionList
        )

        return Puzzle(
            id = id,
            board = board,
            createdAt = Clock.System.now(),
            isDaily = isDaily,
            dailyDate = null
        )
    }
}

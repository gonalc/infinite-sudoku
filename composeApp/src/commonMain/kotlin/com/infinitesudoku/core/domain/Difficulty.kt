package com.infinitesudoku.core.domain

enum class Difficulty(val minClues: Int, val maxClues: Int) {
    EASY(36, 45),
    MEDIUM(32, 35),
    HARD(28, 31),
    EXPERT(22, 27)
}

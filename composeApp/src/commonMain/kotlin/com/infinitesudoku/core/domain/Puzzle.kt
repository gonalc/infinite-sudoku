package com.infinitesudoku.core.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

data class Puzzle(
    val id: Long,
    val board: Board,
    val createdAt: Instant,
    val isDaily: Boolean,
    val dailyDate: LocalDate?
) {
    val isExpired: Boolean
        get() = isDaily && dailyDate != Clock.System.todayIn(TimeZone.currentSystemDefault())
}

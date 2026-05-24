package com.infinitesudoku.feature.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameViewModel(
    private val reducer: GameReducer
) : ViewModel() {

    private val _state = MutableStateFlow(GameState.Initial)
    val state: StateFlow<GameState> = _state.asStateFlow()

    private var timerJob: Job? = null

    fun dispatch(intent: GameIntent) {
        val newState = reducer.reduce(_state.value, intent)
        _state.value = newState

        // Handle side effects
        handleSideEffects(intent, newState)
    }

    private fun handleSideEffects(intent: GameIntent, state: GameState) {
        when (intent) {
            is GameIntent.LoadPuzzle -> startTimer()
            is GameIntent.Pause -> pauseTimer()
            is GameIntent.Resume -> startTimer()
            else -> {}
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                if (!_state.value.isPaused && !_state.value.isCompleted) {
                    dispatch(GameIntent.TimerTick)
                }
            }
        }
    }

    private fun pauseTimer() {
        // Timer continues running but TimerTick is ignored when paused
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

package com.example.thirdeye.ui.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerViewModel: ViewModel() {

    private val totalSeconds=30*60
    private val _secondLeft= MutableStateFlow(totalSeconds)
    val secondLeft=_secondLeft.asStateFlow()
    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (_secondLeft.value>0){
                delay(1000)
                _secondLeft.value-=1

            }
        }
    }
    fun resetTimer(){

        _secondLeft.value=totalSeconds
        startTimer()

    }
}
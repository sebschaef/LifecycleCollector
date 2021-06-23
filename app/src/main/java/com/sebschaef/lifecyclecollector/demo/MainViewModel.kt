package com.sebschaef.lifecyclecollector.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MainViewModel : ViewModel() {

    /**
     * Emits approximately once a second a new value. Always starting from 0.
     */
    val secondsSinceObservedFlow = flow {
        var seconds = 0
        while (true) {
            emit(seconds)
            seconds++
            delay(1000L)
        }
    }

    /**
     * A [StatFlow], which keeps the [secondsSinceObservedFlow] active inside the
     * [viewModelScope] independently from the view's lifecycle.
     */
    val secondsSinceFirstObservation = secondsSinceObservedFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = 0
    )

    /**
     * Emits approximately once a second a new value. The latest value is kept
     * inside [totalObservationTimeSeconds].
     */
    private var totalObservationTimeSeconds = 0
    val secondsOfTotalObservation = flow {
        while (true) {
            emit(totalObservationTimeSeconds)
            totalObservationTimeSeconds++
            delay(1000L)
        }
    }

}
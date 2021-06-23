package com.sebschaef.lifecyclecollector

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * The given flow is only collected when the [owner] is in the
 * [Lifecycle.State.STARTED].
 *
 * As soon as the [owner] leaves that state, the flow collection
 * is cancelled. It will restart if it returns to that state.
 *
 * Note: This is the exact same behaviour as a LiveData observer
 * has it.
 */
inline fun <T> Flow<T>.collectWhenStarted(
    owner: LifecycleOwner,
    crossinline action: suspend (T) -> Unit
) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect {
                action(it)
            }
        }
    }
}

/**
 * The given flow is only collected when the [owner] is in the
 * [Lifecycle.State.RESUMED].
 *
 * As soon as the [owner] leaves that state, the flow collection
 * is cancelled. It will restart if it returns to that state.
 */
inline fun <T> Flow<T>.collectWhenResumed(
    owner: LifecycleOwner,
    crossinline action: suspend (T) -> Unit
) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            collect {
                action(it)
            }
        }
    }
}

/**
 * The given flow is only collected when the [owner] is in the
 * [Lifecycle.State.CREATED].
 *
 * As soon as the [owner] leaves that state, the flow collection
 * is cancelled. It will restart if it returns to that state.
 */
inline fun <T> Flow<T>.collectWhenCreated(
    owner: LifecycleOwner,
    crossinline action: suspend (T) -> Unit
) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            collect {
                action(it)
            }
        }
    }
}
package ru.aeyu.searchimagestest.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import ru.aeyu.searchimagestest.BuildConfig

abstract class BaseViewModel<UiState: FragmentListenableState, UiEffects: FragmentListenableEffects>(initialState: UiState) : ViewModel() {

    protected abstract val classTag: String
//    protected abstract val

    protected val fragmentState: MutableStateFlow<UiState> =  MutableStateFlow(initialState)

    val fragmentStateListener: StateFlow<UiState> = fragmentState.asStateFlow()

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            processCoroutineErrors(throwable)
        }

    protected val mainContext = Dispatchers.Main + coroutineExceptionHandler
    protected val ioContext = Dispatchers.IO + coroutineExceptionHandler

    protected val fragmentEffects: Channel<UiEffects> = Channel()

    val fragmentEffectsListener: Flow<UiEffects> = fragmentEffects.receiveAsFlow()

    protected abstract fun processCoroutineErrors(throwable: Throwable)

    protected val currentState: UiState get() = fragmentState.asStateFlow().value

    protected fun printLog(message: String) {
        if (BuildConfig.DEBUG)
            Log.i("!!!###!!!", "[$classTag] $message")
    }
}
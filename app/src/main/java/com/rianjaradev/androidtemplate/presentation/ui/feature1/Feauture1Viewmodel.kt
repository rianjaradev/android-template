package com.rianjaradev.androidtemplate.presentation.ui.feature1

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.rianjaradev.androidtemplate.core.exception.NetworkError
import com.rianjaradev.androidtemplate.core.exception.ServerError
import com.rianjaradev.androidtemplate.di.IODispatcher
import com.rianjaradev.androidtemplate.domain.entity.Feature1Params
import com.rianjaradev.androidtemplate.domain.interactor.Feature1UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@HiltViewModel
class Feauture1Viewmodel @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val feature1UseCase: Feature1UseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var scope: CoroutineScope? = null

    companion object {
        const val STATE_KEY = "STATE_KEY"
    }

    private var _response: MutableStateFlow<Feauture1UI> = MutableStateFlow(
        savedStateHandle[STATE_KEY] ?: Feauture1UI()
    )

    var response: Feauture1UI
        get() = _response.value
        set(value) {
            _response.value = value
            savedStateHandle[STATE_KEY] = value
        }

    val uIAction = Channel<Feature1Action>(Channel.BUFFERED)

    private val _uiState = MutableSharedFlow<Feature1UIState>()

    val uiState: SharedFlow<Feature1UIState>
        get() = _uiState.asSharedFlow()

    init {
        handleIntent()
    }

    private fun handleIntent() {

        viewModelScope.launch {
            uIAction.consumeAsFlow().collectLatest { event ->

                when (event) {
                    Feature1Action.Fetch -> {
                        scope = CoroutineScope(Job() + dispatcher)
                        launchCoroutineInNewScope(scope!!) {
                            fetch(Feature1Params())
                        }
                    }
                    Feature1Action.CancelFetch -> {
                        scope?.cancel(
                            message = "Canceled by user",
                        )
                        _uiState.emit(Feature1UIState.CleanResponse)
                    }
                }
            }
        }
    }

    private fun launchCoroutineInNewScope(
        scope: CoroutineScope,
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): CoroutineScope {
        scope.launch(
            context = context,
            start = start,
            block = block,
        )
        return scope
    }

    private suspend fun fetch(params: Feature1Params) {
        feature1UseCase.invoke(params)
            .onSuccess {
                _uiState.emit(Feature1UIState.ShowResponse(it))
            }.onFailure { error ->
                val tag = "TEMPLATE"
                when (error) {
                    is NetworkError -> Log.e(tag, error.message)
                    is ServerError -> Log.e(tag, error.message)
                }
            }
    }

}
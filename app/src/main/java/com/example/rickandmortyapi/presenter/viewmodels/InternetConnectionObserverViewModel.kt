package com.example.rickandmortyapi.presenter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.utils.InternetConnectionObserver
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternetConnectionObserverViewModel @Inject constructor(
    private val internetConnectionObserver: InternetConnectionObserver
): ViewModel() {

    private var privateConnectionState: MutableSharedFlow<Boolean>
    = MutableSharedFlow(replay = 2, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val connectionState: SharedFlow<Boolean> = privateConnectionState

    init{
        observeInternetConnection()
    }

    private fun observeInternetConnection(){
        viewModelScope.launch{
            internetConnectionObserver.observe().collect{
                privateConnectionState.emit(it)
            }
        }
    }

}
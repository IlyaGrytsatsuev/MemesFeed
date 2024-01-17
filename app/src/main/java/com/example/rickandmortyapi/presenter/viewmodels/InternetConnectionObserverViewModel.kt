package com.example.rickandmortyapi.presenter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.presenter.ui.InternetState
import com.example.rickandmortyapi.utils.InternetConnectionObserver
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Deque
import javax.inject.Inject
import javax.inject.Singleton

//todo сделать абстрактным, сделать метод для переопределения в наследниках
@Singleton
class InternetConnectionObserverViewModel @Inject constructor(
    private val internetConnectionObserver: InternetConnectionObserver
): ViewModel() {

    private var privateConnectionState: MutableSharedFlow<InternetState>
    = MutableSharedFlow(
//        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val connectionState: Flow<InternetState> = privateConnectionState

    private val internetBoolStatesQueue: MutableList<Boolean> = mutableListOf()


    init{
        getInitialNetworkStatus()
        observeInternetConnection()
    }

    private fun isInternetConnectionRestored(): Boolean {
        if(internetBoolStatesQueue.size == 2)
            return internetBoolStatesQueue.firstOrNull() == false &&
                    internetBoolStatesQueue.lastOrNull() == true
        return false

    }

    private fun observeInternetConnection(){
        viewModelScope.launch{
            internetConnectionObserver.observe().collect{
                addNetworkStatusToTheQueue(it)
                val internetState =
                    when{
                        isInternetConnectionRestored() ->
                            InternetState.InternetRestored()
                        it -> InternetState.HasInternet()
                        else ->  InternetState.InternetLost()
                    }
                privateConnectionState.emit(internetState)

            }
        }
    }

    private fun addNetworkStatusToTheQueue(value: Boolean){
        if(internetBoolStatesQueue.size == 2)
            internetBoolStatesQueue.removeFirst()
        internetBoolStatesQueue.add(value)
    }
    private fun getInitialNetworkStatus(){
        viewModelScope.launch{
            val networkStatus = internetConnectionObserver
                .getInitialNetworkStatus()
            addNetworkStatusToTheQueue(networkStatus)
            val networkState =
                if(networkStatus)
                    InternetState.HasInternet()
                else InternetState.NoInternet()
            privateConnectionState
                .emit(networkState)
        }
    }
}
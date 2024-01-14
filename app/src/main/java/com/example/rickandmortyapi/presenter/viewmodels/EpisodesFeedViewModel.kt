package com.example.rickandmortyapi.presenter.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.domain.usecases.GetCurPageUseCase
import com.example.rickandmortyapi.domain.usecases.GetDisplayedItemsNumUseCase
import com.example.rickandmortyapi.domain.usecases.GetEpisodesListUseCase
import com.example.rickandmortyapi.domain.usecases.ResetPaginationDataUseCase
import com.example.rickandmortyapi.presenter.State
import com.example.rickandmortyapi.utils.NullReceivedException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesFeedViewModel @Inject constructor(
    private val getEpisodesListUseCase: GetEpisodesListUseCase,
    private val resetPaginationDataUseCase: ResetPaginationDataUseCase,
    private val getDisplayedItemsNumUseCase: GetDisplayedItemsNumUseCase,
    private val getCurPageUseCase: GetCurPageUseCase
): ViewModel() {

    private var privateEpisodesList: MutableSharedFlow<State<List<RecyclerModel>>>
    = MutableSharedFlow(replay = 3,
        onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val episodesList: Flow<State<List<RecyclerModel>>> = privateEpisodesList

    private var pageLoadJob: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            privateEpisodesList.emit(
                when(throwable){
                    is NullReceivedException -> State.Empty()
                    else -> State.Error()
                }
            )
        }
    }

    init {
        getEpisodes()
    }
    fun getEpisodes(){
        pageLoadJob = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            privateEpisodesList.emit(State.Loading())
                val loadedList = getEpisodesListUseCase.execute()
                 as List<RecyclerModel>
                val loadedListState = if(loadedList.isEmpty())
                    State.Empty() else State
                    .Success(loadedList)
                privateEpisodesList.emit(loadedListState)

        }
    }

    fun isRepeatedErrorState(): Boolean =
        privateEpisodesList
            .replayCache.firstOrNull() is State.Error

    fun isLoadingState():Boolean = privateEpisodesList
        .replayCache.lastOrNull() is State.Loading
    private fun resetPaginationData(){
        resetPaginationDataUseCase.execute()
    }

    fun getDisplayedItemsNum() =
        getDisplayedItemsNumUseCase.execute()

    fun isFirstPageLoaded() = getCurPageUseCase.execute() == 2
    fun reloadEpisodesList(){
        viewModelScope.launch {
            pageLoadJob?.cancel()
            resetPaginationData()
            getEpisodes()
        }
    }


}
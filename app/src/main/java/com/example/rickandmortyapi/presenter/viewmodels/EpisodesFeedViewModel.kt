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
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
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

    val episodesList: SharedFlow<State<List<RecyclerModel>>> = privateEpisodesList

    private var pageLoadJob: Job? = null

    init {
        getEpisodes()
    }
    fun getEpisodes(){
        pageLoadJob = viewModelScope.launch(Dispatchers.IO) {
            privateEpisodesList.emit(State.Loading())
            try{
                val loadedList = getEpisodesListUseCase.execute()
                 as List<RecyclerModel>
                val loadedListState = if(loadedList.isEmpty())
                    State.Empty() else State
                    .Success(loadedList)
                privateEpisodesList.emit(loadedListState)
            }
            catch (c:CancellationException){
                Log.d("netList", "cancelled")
            }
            catch (e:Exception){
                privateEpisodesList.emit(State
                    .Error(null))
            }
        }
    }

    private fun resetPaginationData(){
        resetPaginationDataUseCase.execute()
    }

    fun getDisplayedItemsNum() =
        getDisplayedItemsNumUseCase.execute()

    fun getCurPage() = getCurPageUseCase.execute()
    fun reloadEpisodesList(){
        viewModelScope.launch {
            pageLoadJob?.cancel()
            resetPaginationData()
            getEpisodes()
        }
    }


}
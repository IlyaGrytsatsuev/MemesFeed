package com.example.rickandmortyapi.presenter.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.domain.models.DetailsModel
import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.domain.usecases.GetEpisodeDetailsUseCase
import com.example.rickandmortyapi.presenter.State
import com.example.rickandmortyapi.utils.NullReceivedException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeDetailsViewModel @Inject constructor(
    private val episodeId:Int,
    private val getEpisodeDetailsUseCase: GetEpisodeDetailsUseCase
):ViewModel() {

    private var privateCurEpisode: MutableStateFlow<State<DetailsModel>>
            = MutableStateFlow(State.Loading())

    val curEpisode: StateFlow<State<DetailsModel>> = privateCurEpisode

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            privateCurEpisode.emit(
                when(throwable){
                    is NullReceivedException -> State.Empty()
                    else -> State.Error()
                }
            )
        }
    }

    init{
        getEpisodeDetails()
        Log.d("netlist", "init is called")
    }

    fun getEpisodeDetails(){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                Log.d("netList",
                    "id in viewModel = $episodeId")
                privateCurEpisode.value = State.Loading()
                val loadedEpisode = getEpisodeDetailsUseCase.execute(episodeId)
                privateCurEpisode.value = if(loadedEpisode ==
                    EpisodeDetailsModel.newEmptyInstance()) State.Empty()
                else State.Success(loadedEpisode)

        }
    }
}
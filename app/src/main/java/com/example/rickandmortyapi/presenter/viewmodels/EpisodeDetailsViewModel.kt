package com.example.rickandmortyapi.presenter.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.domain.usecases.GetEpisodeDetailsUseCase
import com.example.rickandmortyapi.presenter.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeDetailsViewModel @Inject constructor(
    private val episodeId:Int,
    private val getEpisodeDetailsUseCase: GetEpisodeDetailsUseCase
):ViewModel() {

    private var privateCurEpisode: MutableStateFlow<State<RecyclerModel?>>
            = MutableStateFlow(State.Loading())

    val curEpisode: StateFlow<State<RecyclerModel?>> = privateCurEpisode


    init{
        getEpisodeDetails()
        Log.d("netlist", "init is called")
    }

    fun getEpisodeDetails(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("netList",
                    "id in viewModel = $episodeId")
                privateCurEpisode.value = State.Loading()
                privateCurEpisode.value =
                    getEpisodeDetailsUseCase.execute(episodeId)
                as State<RecyclerModel?>

            }
            catch (e:Exception){
                privateCurEpisode.value = State.Error(null)
            }
        }

    }
}
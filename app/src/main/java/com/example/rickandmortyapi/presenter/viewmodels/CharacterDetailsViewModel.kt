package com.example.rickandmortyapi.presenter.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.DetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.domain.usecases.GetCharacterDetailsUseCase
import com.example.rickandmortyapi.presenter.State
import com.example.rickandmortyapi.utils.NullReceivedException
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CharacterDetailsViewModel @Inject constructor
    (private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
     private val characterId:Int): ViewModel() {


    private var privateCurCharacter: MutableStateFlow<State<DetailsModel?>>
            = MutableStateFlow(State.Loading())

    val curCharacter: StateFlow<State<DetailsModel?>> = privateCurCharacter

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            privateCurCharacter.emit(
                when(throwable){
                    is NullReceivedException -> State.Empty()
                    else -> State.Error()
                }
            )
        }
    }

    init{
        getCharacterDetails()
        Log.d("netlist", "init is called")
    }

    fun getCharacterDetails(){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                privateCurCharacter.value = State.Loading()
                val loadedCharacter = getCharacterDetailsUseCase.execute(characterId)
                privateCurCharacter.value = if(loadedCharacter ==
                    CharacterDetailsModel.newEmptyInstance()) State.Empty() else
                        State.Success(loadedCharacter)
        }

    }

}
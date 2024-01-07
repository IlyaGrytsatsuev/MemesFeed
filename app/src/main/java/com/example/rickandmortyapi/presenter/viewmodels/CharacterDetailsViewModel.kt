package com.example.rickandmortyapi.presenter.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.domain.usecases.GetCharacterDetailsUseCase
import com.example.rickandmortyapi.presenter.State
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
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


    private var privateCurCharacter: MutableStateFlow<State<RecyclerModel?>>
            = MutableStateFlow(State.Loading())

    val curCharacter: StateFlow<State<RecyclerModel?>> = privateCurCharacter


    init{
        getCharacterDetails()
        Log.d("netlist", "init is called")
    }

    fun getCharacterDetails(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                privateCurCharacter.value = State.Loading()
                val loadedCharacter = getCharacterDetailsUseCase.execute(characterId)
                privateCurCharacter.value = if(loadedCharacter.isNullReceived
                    || loadedCharacter == CharacterDetailsModel()) State.Empty() else
                        State.Success(loadedCharacter)
            }
            catch (e:Exception){
                privateCurCharacter.value = State.Error()
            }
        }

    }

}
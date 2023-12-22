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

     private fun getCharacterDetails(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("netList",
                    "id in viewModel = $characterId")
                privateCurCharacter.value = State.Loading()
                privateCurCharacter.value =
                    getCharacterDetailsUseCase.execute(characterId)
//                Log.d("netList",
//                    "status = ${privateCurCharacter.value}" +
//                            " details = ${privateCurCharacter.value.data as CharacterDetailsModel}")
            }
            catch (e:Exception){
                privateCurCharacter.value = State.Error(null)
            }
        }

    }
}
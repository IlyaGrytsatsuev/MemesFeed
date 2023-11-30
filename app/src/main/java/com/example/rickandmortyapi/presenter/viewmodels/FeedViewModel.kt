package com.example.rickandmortyapi.presenter.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.domain.usecases.GetCharactersListUseCase
import com.example.rickandmortyapi.presenter.State
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedViewModel @Inject constructor(
    private val getCharactersListUseCase: GetCharactersListUseCase,
    //private val internetConnectionChecker: InternetConnectionChecker,
): ViewModel() {

    private val privateRecyclerList:
            MutableStateFlow<State<List<RecyclerModel>>>
    = MutableStateFlow(State.Loading())

    val charactersList:StateFlow<State<List<RecyclerModel>>> = privateRecyclerList

    private val privateCharacterGenderFilter:
            MutableStateFlow<CharacterGender>
            = MutableStateFlow(CharacterGender.UNCHOSEN)

    val characterGenderFilter:StateFlow<CharacterGender> = privateCharacterGenderFilter

    private val privateCharacterStatusFilter:
            MutableStateFlow<CharacterStatus>
            = MutableStateFlow(CharacterStatus.UNCHOSEN)

    val characterStatusFilter:StateFlow<CharacterStatus> = privateCharacterStatusFilter

    private val privateNameState: MutableStateFlow<String?> = MutableStateFlow(null)

    val nameState:StateFlow<String?> = privateNameState


    init{
        getCharacters()
        Log.d("net", "init is called")
    }


    fun setCharacterStatusFilter(value:CharacterStatus){
        privateCharacterStatusFilter.value = value
    }

    fun setCharacterGenderFilter(value: CharacterGender){
        privateCharacterGenderFilter.value = value
    }

    fun getCharacters(){
        privateRecyclerList.value = State.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                privateRecyclerList.value = State
                    .Success(getCharactersListUseCase.execute(
                        name = privateNameState.value,
                        status = privateCharacterStatusFilter.value,
                        gender = privateCharacterGenderFilter.value
                    ))
            }
            catch (e:Exception){
                privateRecyclerList.value = State
                    .Error()
            }
        }

    }

    fun clearPaginationData(){
        getCharactersListUseCase.clearPaginationData()
    }

//    private fun checkInternetConnection() =
//        viewModelScope.launch {
//            privateCharactersList.value = internetConnectionChecker.checkInternetConnection()
//        }


}
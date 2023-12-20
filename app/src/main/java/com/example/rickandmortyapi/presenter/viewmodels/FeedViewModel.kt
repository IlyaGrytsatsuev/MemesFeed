package com.example.rickandmortyapi.presenter.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.domain.usecases.GetCharacterDetailsUseCase
import com.example.rickandmortyapi.domain.usecases.GetCharactersListUseCase
import com.example.rickandmortyapi.domain.usecases.GetCurPageUseCase
import com.example.rickandmortyapi.domain.usecases.GetDisplayedItemsNumUseCase
import com.example.rickandmortyapi.domain.usecases.ResetPaginationDataUseCase
import com.example.rickandmortyapi.presenter.State
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.Exception

@Singleton
class FeedViewModel @Inject constructor(
    private val getCharactersListUseCase: GetCharactersListUseCase,
    private val resetPaginationDataUseCase: ResetPaginationDataUseCase,
    private val getDisplayedItemsNumUseCase: GetDisplayedItemsNumUseCase,
    private val getCurPageUseCase: GetCurPageUseCase
): ViewModel() {

    private var pageLoadJob: Job? = null
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
        Log.d("netlist", "init is called")
    }

    fun setCharacterStatusFilter(value:CharacterStatus){
        privateCharacterStatusFilter.value = value
    }

    fun setCharacterGenderFilter(value: CharacterGender){
        privateCharacterGenderFilter.value = value
    }

    fun getCharacters(){
        privateRecyclerList.value = State.Loading()
        pageLoadJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val loadedList = getCharactersListUseCase.execute(
                    name = privateNameState.value,
                    status = privateCharacterStatusFilter.value,
                    gender = privateCharacterGenderFilter.value
                )
                privateRecyclerList.value = if(loadedList.isEmpty())
                    State.Empty() else State
                        .Success(loadedList)
            }
            catch (c:CancellationException){
                Log.d("netList", "cancelled")
            }
            catch (e:Exception){
                privateRecyclerList.value = State
                    .Error()
            }
        }

    }

    private fun resetPaginationData(){
        resetPaginationDataUseCase.execute()
    }

    fun getDisplayedItemsNum() =
        getDisplayedItemsNumUseCase.execute()

    fun getCurPage() = getCurPageUseCase.execute()
    fun reloadCharactersList(){
        viewModelScope.launch {
            pageLoadJob?.cancel()
            resetPaginationData()
            getCharacters()
        }
    }

//    private fun checkInternetConnection() =
//        viewModelScope.launch {
//            privateCharactersList.value = internetConnectionChecker.checkInternetConnection()
//        }


}
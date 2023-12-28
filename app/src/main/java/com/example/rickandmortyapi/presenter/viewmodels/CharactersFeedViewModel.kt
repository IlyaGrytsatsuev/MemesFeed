package com.example.rickandmortyapi.presenter.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.domain.models.RecyclerModel
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
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.Exception

@Singleton
class CharactersFeedViewModel @Inject constructor(
    private val getCharactersListUseCase: GetCharactersListUseCase,
    private val resetPaginationDataUseCase: ResetPaginationDataUseCase,
    private val getDisplayedItemsNumUseCase: GetDisplayedItemsNumUseCase,
    private val getCurPageUseCase: GetCurPageUseCase
): ViewModel() {

    private var pageLoadJob: Job? = null
    private val privateRecyclerList:
            MutableSharedFlow<State<List<RecyclerModel>>>
    = MutableSharedFlow(replay = 3,
        onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val charactersList: SharedFlow<State<List<RecyclerModel>>>
    = privateRecyclerList
    //TODO val FLOW INstead of sharedFlow

    private val privateCharacterGenderFilter:
            MutableSharedFlow<CharacterGender>
            = MutableSharedFlow(replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val characterGenderFilter:SharedFlow<CharacterGender>
    = privateCharacterGenderFilter

    private val privateCharacterStatusFilter:
            MutableSharedFlow<CharacterStatus> =
            MutableSharedFlow(replay = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val characterStatusFilter:SharedFlow<CharacterStatus>
    = privateCharacterStatusFilter

    private val privateNameState: MutableSharedFlow<String?>
    = MutableSharedFlow(replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val nameState:SharedFlow<String?> = privateNameState

    init{
        getCharacters()
        Log.d("netlist", "init is called")
    }

    fun setCharacterStatusFilter(value:CharacterStatus){
        viewModelScope.launch {
            privateCharacterStatusFilter.emit(value)
            Log.d("netlist",
                privateCharacterStatusFilter
                    .replayCache.first().text?:"Not chosen")
        }
    }

    fun setCharacterGenderFilter(value: CharacterGender){
        viewModelScope.launch {
            privateCharacterGenderFilter.emit(value)
        }
    }

    fun getCharacters(){
        pageLoadJob = viewModelScope.launch(Dispatchers.IO) {
            privateRecyclerList.emit(State.Loading())
            try {
                Log.d("netlist","statusFilter before request = ${privateCharacterGenderFilter.replayCache.lastOrNull()}")
                val loadedList = getCharactersListUseCase.execute(
                    name = privateNameState.replayCache.lastOrNull(),
                    status = privateCharacterStatusFilter.replayCache.lastOrNull(),
                    gender = privateCharacterGenderFilter.replayCache.lastOrNull()
                ) as List<RecyclerModel>
                val loadedListState = if(loadedList.isEmpty())
                    State.Empty() else State
                    .Success(loadedList)
                privateRecyclerList.emit(loadedListState)
                Log.d("netlist", "emitted List " +
                        "${privateRecyclerList.replayCache.first()}")
            }
            catch (c:CancellationException){
                Log.d("netList", "cancelled")
            }
            catch (e:Exception){
                privateRecyclerList.emit(State
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
    fun reloadCharactersList(){
        viewModelScope.launch {
            pageLoadJob?.cancel()
            resetPaginationData()
            getCharacters()
        }
    }

}
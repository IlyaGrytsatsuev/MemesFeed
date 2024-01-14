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
import com.example.rickandmortyapi.utils.InternetConnectionObserver
import com.example.rickandmortyapi.utils.NullReceivedException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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
    private val privateCharactersList:
            MutableSharedFlow<State<List<RecyclerModel>>>
    = MutableSharedFlow(replay = 3,
        onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val charactersList: Flow<State<List<RecyclerModel>>>
    = privateCharactersList

    private val privateCharacterGenderFilter:
            MutableSharedFlow<CharacterGender>
            = MutableSharedFlow(replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val characterGenderFilter: Flow<CharacterGender>
    = privateCharacterGenderFilter

    private val privateCharacterStatusFilter:
            MutableSharedFlow<CharacterStatus> =
            MutableSharedFlow(replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val characterStatusFilter: Flow<CharacterStatus>
    = privateCharacterStatusFilter

    private val privateNameState: MutableSharedFlow<String?>
    = MutableSharedFlow(replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val nameState: Flow<String?> = privateNameState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            privateCharactersList.emit(
                when(throwable){
                    is NullReceivedException -> State.Empty()
                    else -> State.Error()
                }
            )
        }
    }
    init{
        getCharacters()
        Log.d("netlist", "init is called")
    }

    fun setCharacterStatusFilter(value:CharacterStatus){
        viewModelScope.launch {
            privateCharacterStatusFilter.emit(value)
        }
    }

    fun setCharacterGenderFilter(value: CharacterGender){
        viewModelScope.launch {
            privateCharacterGenderFilter.emit(value)
        }
    }

    fun setCharacterNameFilter(value: String){
        viewModelScope.launch {
            privateNameState.emit(value)
        }
    }

    fun isRepeatedErrorState(): Boolean =
        privateCharactersList
            .replayCache.firstOrNull() is State.Error

    fun isLoadingState():Boolean = privateCharactersList
        .replayCache.lastOrNull() is State.Loading

    fun getCharacters(){
        pageLoadJob = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            privateCharactersList.emit(State.Loading())

            Log.d("netlist","statusFilter before request " +
                    "= ${privateNameState.replayCache.lastOrNull()}")

            val loadedList = getCharactersListUseCase.execute(
                name = privateNameState.replayCache.lastOrNull(),
                status = privateCharacterStatusFilter.replayCache.lastOrNull(),
                gender = privateCharacterGenderFilter.replayCache.lastOrNull()
            ) as List<RecyclerModel>

            val loadedListState = if(loadedList.isEmpty())
                State.Empty() else State.Success(loadedList)
            privateCharactersList.emit(loadedListState)
            Log.d("netlist", "emitted List " +
                    "${privateCharactersList.replayCache.first()}")


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
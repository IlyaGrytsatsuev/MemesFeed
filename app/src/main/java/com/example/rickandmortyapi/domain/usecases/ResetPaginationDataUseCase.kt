package com.example.rickandmortyapi.domain.usecases

import android.util.Log
import com.example.rickandmortyapi.domain.repository.PaginationDataRepository
import javax.inject.Inject

class ResetPaginationDataUseCase @Inject constructor
    (private val paginationDataRepository: PaginationDataRepository) {

        fun execute(){
            paginationDataRepository.resetData()
            Log.d("netlist", "reset Pagination")
        }
}
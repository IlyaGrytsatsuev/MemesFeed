package com.example.rickandmortyapi.domain.usecases

import com.example.rickandmortyapi.domain.repository.PaginationDataRepository
import javax.inject.Inject

class GetDisplayedItemsNumUseCase @Inject constructor
    (private val paginationDataRepository: PaginationDataRepository) {

        fun execute() =
            paginationDataRepository.getDisplayedItemsNum()

}
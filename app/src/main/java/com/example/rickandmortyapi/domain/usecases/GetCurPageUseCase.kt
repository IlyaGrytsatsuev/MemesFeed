package com.example.rickandmortyapi.domain.usecases

import com.example.rickandmortyapi.domain.repository.PaginationDataRepository
import javax.inject.Inject

class GetCurPageUseCase @Inject constructor
    (private val paginationDataRepository: PaginationDataRepository) {

        fun execute() = paginationDataRepository.getCurPage()
}

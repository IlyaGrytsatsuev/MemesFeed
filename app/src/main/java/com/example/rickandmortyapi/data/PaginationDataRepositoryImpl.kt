package com.example.rickandmortyapi.data

import com.example.rickandmortyapi.domain.repository.PaginationDataRepository
import javax.inject.Inject

class PaginationDataRepositoryImpl @Inject
constructor(private val paginationData: PaginationData): PaginationDataRepository {
    override fun setIsFirstLoadedFromApi(value: Boolean) {
        paginationData.setIsFirstLoadedFromApi(value)
    }

    override fun isFirstLoadedFromApi(): Boolean =
        paginationData.isFirstLoadedFromApi()

    override fun getDisplayedItemsNum() =
        paginationData.getDisplayedItemsNum()


    override fun increaseDisplayedItemsNum() {
        paginationData.increaseDisplayedItemsNum()
    }

    override fun getCurPage() =
        paginationData.getCurPage()


    override fun incrementPageCounter() {
        paginationData.incrementPageCounter()
    }

    override fun getTotalPageNum() =
        paginationData.getTotalPaggeNum()


    override fun setTotalPageNum(value: Int) {
        paginationData.setTotalPageNum(value)
    }

    override fun hasPagesInfo() =
       paginationData.hasPagesInfo()


    override fun setHasPagesInfo() {
        paginationData.setHasPagesInfo()
    }

    override fun resetData() {
        paginationData.resetData()
    }
}
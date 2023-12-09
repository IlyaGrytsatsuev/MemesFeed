package com.example.rickandmortyapi.domain.repository

interface PaginationDataRepository {

    fun getDisplayedItemsNum():Int

    fun increaseDisplayedItemsNum()

    fun getCurPage():Int
    fun incrementPageCounter()

    fun getTotalPageNum() :Int

    fun setTotalPageNum(value:Int)
    fun hasPagesInfo() :Boolean
    fun setHasPagesInfo()

    fun resetData()
}
package com.example.rickandmortyapi.data

import com.example.rickandmortyapi.utils.Constants

class PaginationData{
    private var displayedItemsNum: Int = 0
    private var curPage: Int = 1
    private var totalPageNum: Int = 1
    private var hasPagesInfo:Boolean = false
    private var isFirstLoadedFromApi = true


    fun setIsFirstLoadedFromApi(value: Boolean){
        isFirstLoadedFromApi = value
    }

    fun isFirstLoadedFromApi() = isFirstLoadedFromApi

    fun getDisplayedItemsNum() = displayedItemsNum

    fun increaseDisplayedItemsNum(){
        displayedItemsNum+=Constants.ITEMS_PER_PAGE
    }

     fun getCurPage() = curPage

     fun incremetPageCounter(){
         curPage++
     }

     fun getTotalPaggeNum() = totalPageNum

     fun setTotalPageNum(value:Int){
         totalPageNum  = value
     }
     fun hasPagesInfo() = hasPagesInfo
     fun setHasPagesInfo(){
         hasPagesInfo = true
     }

    fun resetData(){
        curPage = 1
        displayedItemsNum = 0
        totalPageNum = 1
        hasPagesInfo = false
        isFirstLoadedFromApi = true
    }
 }



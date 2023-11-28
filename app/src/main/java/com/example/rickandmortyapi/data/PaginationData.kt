package com.example.rickandmortyapi.data

data class PaginationData(
    var displayedItemsNum: Int = 0,
    var curPage: Int = 1,
    var totalPageNum: Int = 1,
    var hasPagesInfo:Boolean = false,
    var dbIsEmptyFromStart: Boolean = false
)

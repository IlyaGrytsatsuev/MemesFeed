package com.example.rickandmortyapi.presenter.feedRecycler

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
RecyclerView.OnScrollListener(){

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
        if(!isLoading()  && (displayedItemsNum()- lastVisiblePosition) <= 2
            && displayedItemsNum() !=0) {
            getNextPage()
            Log.d("listNetwork", "next page is called")
        }
    }

    abstract fun isLoading():Boolean
    abstract fun getNextPage()
    abstract fun displayedItemsNum(): Int
}
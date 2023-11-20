package com.example.rickandmortyapi.presenter.feedRecycler

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.utils.Constants

abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
RecyclerView.OnScrollListener(){

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
        if(!isLoading() && (itemsNumInCache() - lastVisiblePosition) <= 2
            && itemsNumInCache() !=0) {
            getNextPage()
            Log.d("listNetwork", "page2 is called")
        }
    }

    abstract fun isLoading():Boolean

    abstract fun hasInternetConnection(): Boolean
    abstract fun getNextPage()
    abstract fun itemsNumInCache(): Int
}
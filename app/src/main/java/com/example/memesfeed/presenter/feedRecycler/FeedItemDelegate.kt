package com.example.memesfeed.presenter.feedRecycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.memesfeed.domain.models.MemeModel

interface FeedItemDelegate {

    fun isOfViewType(memeItem:MemeModel):Boolean
    fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun bindViewHolder(viewHolder: ViewHolder, memeItem: MemeModel)
}
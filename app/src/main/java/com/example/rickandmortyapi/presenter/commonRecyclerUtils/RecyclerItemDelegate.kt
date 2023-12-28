package com.example.rickandmortyapi.presenter.commonRecyclerUtils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.rickandmortyapi.domain.models.RecyclerModel

interface RecyclerItemDelegate{

    fun isOfViewType(item: RecyclerModel):Boolean
    fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun bindViewHolder(viewHolder: ViewHolder, item: RecyclerModel,
                       position: Int? = null)
}
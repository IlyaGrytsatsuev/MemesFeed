package com.example.rickandmortyapi.presenter.commonRecyclerUtils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.domain.models.DetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel

interface DetailsRecyclerItemDelegate {
    fun isOfViewType(item: RecyclerModel):Boolean
    fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerModel,
                       position: Int? = null)
}
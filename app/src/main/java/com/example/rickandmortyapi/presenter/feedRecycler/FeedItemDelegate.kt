package com.example.rickandmortyapi.presenter.feedRecycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.rickandmortyapi.domain.models.CharacterModel

interface FeedItemDelegate {

    fun isOfViewType(characterItem:CharacterModel):Boolean
    fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun bindViewHolder(viewHolder: ViewHolder, characterItem: CharacterModel)
}
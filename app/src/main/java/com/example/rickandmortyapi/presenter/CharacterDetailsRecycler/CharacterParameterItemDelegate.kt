package com.example.rickandmortyapi.presenter.CharacterDetailsRecycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.RecyclerItemDelegate

open class CharacterParameterItemDelegate(): RecyclerItemDelegate {
    override fun isOfViewType(item: RecyclerModel): Boolean {
        return true
    }

    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerModel) {

    }
}
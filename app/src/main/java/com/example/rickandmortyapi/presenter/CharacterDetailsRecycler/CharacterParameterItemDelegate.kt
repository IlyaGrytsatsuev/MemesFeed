package com.example.rickandmortyapi.presenter.CharacterDetailsRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.databinding.CharacterDetailsParameterItemBinding
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerItemDelegate

abstract class CharacterParameterItemDelegate(): RecyclerItemDelegate {
    override fun isOfViewType(item: RecyclerModel): Boolean{
        return true
    }

    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CharacterParameterItemViewHolder(
            CharacterDetailsParameterItemBinding.inflate(
                LayoutInflater.from(parent.context)
                , parent, false))
    }

    abstract override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerModel)


}
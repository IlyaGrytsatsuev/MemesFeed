package com.example.rickandmortyapi.presenter.commonRecyclerUtils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.databinding.CharacterDetailsEpisodesListItemBinding
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.models.RecyclerModel

class EpisodesListItemDelegate: RecyclerItemDelegate {
    override fun isOfViewType(item: RecyclerModel): Boolean {
        return item is EpisodeModel
    }

    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return EpisodesListItemViewHolder(
            CharacterDetailsEpisodesListItemBinding.inflate(
                LayoutInflater.from(parent.context)
                , parent, false))
    }

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerModel) {
        (viewHolder as EpisodesListItemViewHolder).onBind(item)
    }
}
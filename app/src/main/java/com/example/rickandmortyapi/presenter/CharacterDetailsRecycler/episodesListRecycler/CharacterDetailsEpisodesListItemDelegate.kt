package com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.episodesListRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.databinding.CharacterDetailsEpisodesListItemBinding
import com.example.rickandmortyapi.databinding.CharacterDetailsParameterItemBinding
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.CharacterParameterItemViewHolder
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerItemDelegate

class CharacterDetailsEpisodesListItemDelegate: RecyclerItemDelegate {
    override fun isOfViewType(item: RecyclerModel): Boolean {
        return item is EpisodeModel
    }

    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CharacterDetailsEpisodesListItemViewHolder(
            CharacterDetailsEpisodesListItemBinding.inflate(
                LayoutInflater.from(parent.context)
                , parent, false))
    }

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerModel) {
        (viewHolder as CharacterDetailsEpisodesListItemViewHolder).onBind(item)
    }
}
package com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.episodesListRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.databinding.CharacterDetailsEpisodesListBinding
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerItemDelegate

class CharacterDetailsEpisodesListDelegate: RecyclerItemDelegate {
    override fun isOfViewType(item: RecyclerModel): Boolean {
        return true
    }

    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CharacterDetailsEpisodesListViewHolder(
            CharacterDetailsEpisodesListBinding.inflate(
                LayoutInflater.from(parent.context)
                , parent, false))
    }

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerModel) {
        (viewHolder as CharacterDetailsEpisodesListViewHolder).onBind(item)
    }
}
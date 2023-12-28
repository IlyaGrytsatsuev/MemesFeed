package com.example.rickandmortyapi.presenter.EpisodeDetailsRecycler.delegates

import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.viewHolders.CharacterParameterItemViewHolder
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.DetailsParameterItemDelegate

class EpisodeParameterItemDelegate: DetailsParameterItemDelegate() {
    override fun bindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        item: RecyclerModel,
        position: Int?
    ) {
        (viewHolder as CharacterParameterItemViewHolder)
            .onBind("Episode:",
                (item as EpisodeDetailsModel).episode)
    }
}
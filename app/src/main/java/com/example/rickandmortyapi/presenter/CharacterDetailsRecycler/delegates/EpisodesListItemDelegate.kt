package com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.databinding.CharacterDetailsEpisodesListItemBinding
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.EpisodeListItemViewHolder
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerItemDelegate

class EpisodesListItemDelegate(
    private val itemChoiceFun:((episodeId:Int)->Unit)? = null)
    : RecyclerItemDelegate {
    override fun isOfViewType(item: RecyclerModel): Boolean {
        return item is EpisodeModel ||
                item is CharacterDetailsModel
    }

    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return EpisodeListItemViewHolder(
            CharacterDetailsEpisodesListItemBinding.inflate(
                LayoutInflater.from(parent.context)
                , parent, false))
    }

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerModel
                                ,position:Int?) {
        if(item is CharacterDetailsModel) {
            if(item.episode.isEmpty())
                (viewHolder as EpisodeListItemViewHolder)
                    .onBind(EpisodeModel(), itemChoiceFun)
            else
                (viewHolder as EpisodeListItemViewHolder)
                    .onBind(item.episode[position ?: 0], itemChoiceFun)
        }


        else
            (viewHolder as EpisodeListItemViewHolder)
                .onBind(item, itemChoiceFun)
    }
}
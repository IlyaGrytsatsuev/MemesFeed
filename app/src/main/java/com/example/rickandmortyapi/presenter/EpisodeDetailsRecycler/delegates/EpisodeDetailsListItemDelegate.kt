package com.example.rickandmortyapi.presenter.EpisodeDetailsRecycler.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.databinding.CharacterDetailsEpisodesListItemBinding
import com.example.rickandmortyapi.databinding.FeedRecyclerCharacterItemBinding
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.EpisodeListItemViewHolder
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerItemDelegate
import com.example.rickandmortyapi.presenter.feedRecycler.CharacterFeedRecyclerViewHolder

class EpisodeDetailsListItemDelegate
    (private val itemChoiceFun:((episodeId:Int)->Unit)? = null)
    : RecyclerItemDelegate {
    override fun isOfViewType(item: RecyclerModel): Boolean {
        return item is EpisodeDetailsModel
    }

    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CharacterFeedRecyclerViewHolder(
            FeedRecyclerCharacterItemBinding.inflate(
                LayoutInflater.from(parent.context)
                , parent, false))
    }

    override fun bindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        item: RecyclerModel,
        position: Int?
    ) {
        (viewHolder as CharacterFeedRecyclerViewHolder)
            .onBind((item as EpisodeDetailsModel)
                .characters[position?:0], itemChoiceFun)
    }
}
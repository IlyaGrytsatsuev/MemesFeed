package com.example.rickandmortyapi.presenter.commonRecyclerUtils

import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.databinding.CharacterDetailsEpisodesListItemBinding
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.models.RecyclerModel

class EpisodeListItemViewHolder(
    private val binding: CharacterDetailsEpisodesListItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: RecyclerModel,
               itemChoiceFun:((episodeId:Int)->Unit)?){
        binding.episodeName.text = (item as EpisodeModel).name
        binding.episodeSerial.text = (item as EpisodeModel).episode

        itemView.setOnClickListener {
            if (itemChoiceFun != null) {
                itemChoiceFun(item.id)
            }
        }
    }


}
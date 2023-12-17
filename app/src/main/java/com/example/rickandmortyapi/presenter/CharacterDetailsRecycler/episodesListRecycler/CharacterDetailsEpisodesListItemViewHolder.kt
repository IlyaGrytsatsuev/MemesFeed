package com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.episodesListRecycler

import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.databinding.CharacterDetailsEpisodesListItemBinding
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.models.RecyclerModel

class CharacterDetailsEpisodesListItemViewHolder(
    private val binding: CharacterDetailsEpisodesListItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: RecyclerModel){
        binding.episodeName.text = (item as EpisodeModel).name
        binding.episodeSerial.text = (item as EpisodeModel).episode
    }
}
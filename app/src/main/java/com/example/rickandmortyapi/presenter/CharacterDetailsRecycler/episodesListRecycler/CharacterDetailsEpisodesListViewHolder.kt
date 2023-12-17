package com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.episodesListRecycler

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.databinding.CharacterDetailsEpisodesListBinding
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerListAdapter

class CharacterDetailsEpisodesListViewHolder(
    private val binding: CharacterDetailsEpisodesListBinding
)
    : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: RecyclerModel){
            val delegatesList = listOf(
                CharacterDetailsEpisodesListItemDelegate()
            )
            binding.characterDetailsEpisodesList.adapter =
                RecyclerListAdapter(delegatesList)
            binding.characterDetailsEpisodesList.layoutManager =
                LinearLayoutManager(itemView.context,
                    LinearLayoutManager.VERTICAL, false)

            (binding.characterDetailsEpisodesList.adapter as RecyclerListAdapter)
                .differ.submitList((item as CharacterDetailsModel).episode as List<RecyclerModel>?)
            Log.d("netlist","equality of epiosdesList = ${(item as CharacterDetailsModel).episode.size ==
                    (binding.characterDetailsEpisodesList.adapter as RecyclerListAdapter).differ.currentList.size}")

        }


}
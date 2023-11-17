package com.example.rickandmortyapi.presenter.feedRecycler

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FeedRecyclerStandartItemBinding
import com.example.rickandmortyapi.domain.models.CharacterModel

class FeedRecyclerViewHolder(private val binding: FeedRecyclerStandartItemBinding)
    : RecyclerView.ViewHolder(binding.root) {


    fun onBind(characterModel :CharacterModel, context: Context){
        binding.name.text = characterModel.name
        binding.status.text = characterModel.status
        binding.species.text = characterModel.species
        binding.type.text = characterModel.type
        binding.gender.text = characterModel.gender
        Glide.with(itemView.context)
            .load(characterModel.image)
            .placeholder(R.drawable.glide_placeholder)
            .override(400,400)
            .into(binding.recyclerItemImage)

    }
}
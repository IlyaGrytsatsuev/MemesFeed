package com.example.rickandmortyapi.presenter.feedRecycler

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FeedRecyclerCharacterItemBinding
import com.example.rickandmortyapi.domain.models.CharacterModel

class CharacterFeedRecyclerViewHolder(private val binding: FeedRecyclerCharacterItemBinding)
    : RecyclerView.ViewHolder(binding.root) {


    fun onBind(characterModel :CharacterModel,
               onItemChoiceFun:((characterId:Int)->Unit)?){
        binding.name.text = characterModel.name
        binding.status.text = characterModel.status
        binding.species.text = characterModel.species
        binding.type.text = characterModel.type
        binding.gender.text = characterModel.gender
        Glide.with(itemView.context)
            .load(characterModel.image)
            .placeholder(R.drawable.glide_placeholder)
            .centerCrop()
            .into(binding.recyclerItemImage)

        itemView.setOnClickListener {
            if (onItemChoiceFun != null) {
                onItemChoiceFun(characterModel.id)
            }
        }
    }
}
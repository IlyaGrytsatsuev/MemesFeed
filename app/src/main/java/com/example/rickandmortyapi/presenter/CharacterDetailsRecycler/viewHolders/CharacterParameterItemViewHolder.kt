package com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.databinding.CharacterDetailsParameterItemBinding
import com.example.rickandmortyapi.databinding.FeedRecyclerCharacterItemBinding

class CharacterParameterItemViewHolder(private val binding: CharacterDetailsParameterItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

        fun onBind(title:String, parameter:String){
            binding.parameterTitle.text = title
            binding.parameter.text = parameter
        }
}
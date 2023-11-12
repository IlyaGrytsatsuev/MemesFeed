package com.example.rickandmortyapi.presenter.feedRecycler

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyapi.databinding.FeedRecyclerStandartItemBinding

class FeedRecyclerViewHolder(binding: FeedRecyclerStandartItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    val imgHolder = binding.recyclerItemImage

    fun onBind(memeModel: MemeModel, context: Context){
        Glide.with(itemView.context)
            .load(memeModel.url)
            .override(memeModel.width, memeModel.height)
            .into(imgHolder)
    }
}
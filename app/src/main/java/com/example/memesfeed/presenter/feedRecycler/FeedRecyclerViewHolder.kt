package com.example.memesfeed.presenter.feedRecycler

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.memesfeed.databinding.FeedRecyclerStandartItemBinding
import com.example.memesfeed.domain.models.MemeModel

class FeedRecyclerViewHolder(binding: FeedRecyclerStandartItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    val imgHolder = binding.recyclerItemImage

    fun onBind(memeModel: MemeModel, context: Context){
        Glide.with(context)
            .load(memeModel.url)
            .override(memeModel.width, memeModel.height)
            .into(imgHolder)
    }
}
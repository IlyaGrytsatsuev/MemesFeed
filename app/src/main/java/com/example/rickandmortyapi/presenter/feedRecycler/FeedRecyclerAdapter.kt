package com.example.rickandmortyapi.presenter.feedRecycler

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.domain.models.CharacterModel

class FeedRecyclerAdapter(private val delegates: List<FeedItemDelegate>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegates[viewType].getViewHolder(parent)


    override fun getItemCount() = differ.currentList.size

    override fun getItemViewType(position: Int) : Int {
        if(delegates.size > 1)
            return delegates.indexOfFirst { delegate ->
                delegate.isOfViewType(differ.currentList[position])
            }
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates[getItemViewType(position)]
            .bindViewHolder(holder, differ.currentList[position])
    }

    private val diffUtilCallback = object : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtilCallback)
}
package com.example.memesfeed.presenter.feedRecycler

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.memesfeed.domain.models.MemeModel

class FeedRecyclerAdapter(val delegates: List<FeedItemDelegate>)
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

    private val diffUtilCallback = object : DiffUtil.ItemCallback<MemeModel>() {
        override fun areItemsTheSame(oldItem: MemeModel, newItem: MemeModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MemeModel, newItem: MemeModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtilCallback)
}
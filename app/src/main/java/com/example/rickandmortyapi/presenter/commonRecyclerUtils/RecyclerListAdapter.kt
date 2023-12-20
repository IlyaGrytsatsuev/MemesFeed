package com.example.rickandmortyapi.presenter.commonRecyclerUtils

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.domain.models.RecyclerModel

class RecyclerListAdapter(private val delegates: List<RecyclerItemDelegate>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegates[viewType].getViewHolder(parent)


    override fun getItemCount() = differ.currentList.size

    override fun getItemViewType(position: Int) : Int {
        return delegates.indexOfFirst { delegate ->
            delegate.isOfViewType(differ.currentList[position])
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates[getItemViewType(position)]
            .bindViewHolder(holder, differ.currentList[position])
    }

    private val diffUtilCallback = object : DiffUtil.ItemCallback<RecyclerModel>() {
        override fun areItemsTheSame(oldItem: RecyclerModel, newItem: RecyclerModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RecyclerModel, newItem: RecyclerModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtilCallback)

    fun appendItems(list: List<RecyclerModel>){
        val res = differ.currentList.toMutableList()
        val idsList = mutableListOf<Int>()
        list.forEach{
            res.add(it)
            idsList.add(it.id)
        }
        Log.d("listNetAppend", idsList.toString())
        differ.submitList(res)
    }

}
package com.example.rickandmortyapi.presenter.commonRecyclerUtils

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.domain.models.DetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel

class DetailsRecyclerAdapter (private val delegates: List<RecyclerItemDelegate>, )
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var detailsModel: DetailsModel? = null

    fun setCharacterDetailsModel(value:DetailsModel){
        detailsModel = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : RecyclerView.ViewHolder =
        delegates[viewType].getViewHolder(parent)


    override fun getItemCount(): Int {
        val count  = detailsModel?.let {
            if(it.listSize != 0)
                delegates.size +
                    it.listSize - 1
            else
                delegates.size} ?: 0

        Log.d("netlist", "items count = $count ")
        return count
    }

    override fun getItemViewType(position: Int) : Int {
        if (position < delegates.size - 1)
            return position

        return delegates.size - 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val curListPosition = position - delegates.size + 1
        detailsModel?.let {
            if (position < delegates.size - 1)
                delegates[getItemViewType(position)]
                    .bindViewHolder(holder, it)
            else
                delegates[delegates.size - 1]
                    .bindViewHolder(holder, it, curListPosition)

        }
    }


}
package com.example.rickandmortyapi.presenter.CharacterDetailsRecycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerItemDelegate

class DetailsRecyclerAdapter (private val delegates: List<RecyclerItemDelegate>,
    )
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var characterDetailsModel:RecyclerModel? = null

    fun setCharacterDetailsModel(value:RecyclerModel){
        characterDetailsModel = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : RecyclerView.ViewHolder =
        delegates[viewType].getViewHolder(parent)




    override fun getItemCount(): Int = characterDetailsModel?.let {
        delegates.size +
                (characterDetailsModel as CharacterDetailsModel).episode.size - 1
    }?:0

    override fun getItemViewType(position: Int) : Int {
        if (position < delegates.size - 1)
            return position

        return delegates.size - 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val curEpisodePosition = position - delegates.size + 1
        characterDetailsModel?.let {
            if (position < delegates.size - 1)
                delegates[getItemViewType(position)]
                    .bindViewHolder(holder, it)
            else
                delegates[delegates.size - 1]
                    .bindViewHolder(holder, (it as CharacterDetailsModel)
                        .episode[curEpisodePosition])

        }
    }


}
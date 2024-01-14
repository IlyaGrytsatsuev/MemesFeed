package com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates

import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.viewHolders.CharacterParameterItemViewHolder
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.DetailsParameterItemDelegate

class OriginParameterItemDelegate: DetailsParameterItemDelegate() {
    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder,
                                item: RecyclerModel, position: Int?) {
        (viewHolder as CharacterParameterItemViewHolder)
            .onBind(
                viewHolder.itemView.context.getString(R.string.origin_title),
                (item as CharacterDetailsModel).origin.name)
    }
}
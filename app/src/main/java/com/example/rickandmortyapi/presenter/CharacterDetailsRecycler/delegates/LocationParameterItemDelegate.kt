package com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.delegates

import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.viewHolders.CharacterParameterItemViewHolder

class LocationParameterItemDelegate: CharacterParameterItemDelegate() {
    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerModel) {
        (viewHolder as CharacterParameterItemViewHolder)
            .onBind(
                //Resources.getSystem().getString(R.string.location_title)
                "Location:"
                ,
                (item as CharacterDetailsModel).location.name
            )
    }
}
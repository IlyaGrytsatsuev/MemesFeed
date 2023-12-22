package com.example.rickandmortyapi.presenter.CharacterDetailsRecycler

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel

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
package com.example.rickandmortyapi.presenter.CharacterDetailsRecycler

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.RecyclerModel

class SpeciesParameterItemDelegate: CharacterParameterItemDelegate() {
    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerModel) {
        (viewHolder as CharacterParameterItemViewHolder)
            .onBind(
                //Resources.getSystem().getString(R.string.species_title)
                "Species:"
                ,
                (item as CharacterDetailsModel).species)
    }

}
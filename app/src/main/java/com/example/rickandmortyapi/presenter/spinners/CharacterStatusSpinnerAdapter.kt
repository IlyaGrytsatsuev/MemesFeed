package com.example.rickandmortyapi.presenter.spinners

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.rickandmortyapi.databinding.CustomSpinnerLayoutBinding

class CharacterStatusSpinnerAdapter(context: Context, itemsList:List<String>)
    : ArrayAdapter<String>(context, 0, itemsList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = CustomSpinnerLayoutBinding.inflate(LayoutInflater.from(context),
            parent, false )
        val view = convertView?:binding
        return super.getView(position, convertView, parent)
    }
}
package com.example.rickandmortyapi.presenter.CharacterDetailsRecycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.utils.Constants

class DetailsRecyclerItemDecorator() : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = if (parent.getChildLayoutPosition(view) == 0)
              Constants.CHARACTER_DETAILS_ITEMS_OFFSET else 0

        outRect.left = Constants.CHARACTER_FEED_ITEM_START_AND_END_OFFSET
        outRect.right = Constants.CHARACTER_FEED_ITEM_START_AND_END_OFFSET
        outRect.bottom = Constants.CHARACTER_DETAILS_ITEMS_OFFSET

    }

}
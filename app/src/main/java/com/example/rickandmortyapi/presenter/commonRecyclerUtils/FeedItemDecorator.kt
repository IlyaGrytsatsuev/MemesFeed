package com.example.rickandmortyapi.presenter.commonRecyclerUtils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.utils.Constants

class FeedItemDecorator: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = Constants.CHARACTER_FEED_ITEM_START_AND_END_OFFSET
        outRect.right = Constants.CHARACTER_FEED_ITEM_START_AND_END_OFFSET
        outRect.top = Constants.CHARACTER_FEED_ITEM_TOP_AND_BOTTOM_OFFSET
        outRect.bottom = Constants.CHARACTER_FEED_ITEM_TOP_AND_BOTTOM_OFFSET

    }
}
package com.conan.kotlingank.widget

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class WelfareItemDecoration(space: Int, columns: Int): RecyclerView.ItemDecoration() {

    private var space = space
    private var columns = columns

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val position = parent.getChildAdapterPosition(view)
        outRect.left = 0
        outRect.right = space
        outRect.bottom = space
        outRect.top = 0
        if (isTopChild(position)) {
            outRect.top = space
        }
        if (isLeftChild(position)) {
            outRect.left = space
        }
    }

    private fun isTopChild(position: Int): Boolean {
        return position < columns
    }

    private fun isLeftChild(position: Int): Boolean {
        return position % columns == 0
    }
}
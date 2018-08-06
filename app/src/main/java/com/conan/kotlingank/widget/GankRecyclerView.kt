package com.conan.kotlingank.widget

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet

class GankRecyclerView : RecyclerView {

    var moreListener: OnLoadMoreListener? = null

    var bLoadingMore: Boolean = false


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViews()
    }

    fun initViews() {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = layoutManager
                    if (layoutManager is GridLayoutManager) {
                        val itemCount: Int = layoutManager.itemCount
                        val firstVisibleItemPosition = layoutManager
                                .findFirstVisibleItemPosition()
                        if (firstVisibleItemPosition + childCount >= itemCount && firstVisibleItemPosition > 0) {
                            loadMore()
                        }
                    }
                    if (layoutManager is LinearLayoutManager) {
                        val itemCount = layoutManager.itemCount
                        val firstVisibleItemPosition = layoutManager
                                .findFirstVisibleItemPosition()
                        if (firstVisibleItemPosition + childCount >= itemCount && firstVisibleItemPosition > 0) {
                            loadMore()
                        }
                    }

                    if (layoutManager is StaggeredGridLayoutManager) {
                        val itemCount = layoutManager.itemCount
                        val lastPositions = layoutManager.findFirstVisibleItemPositions(IntArray(layoutManager.spanCount))
                        val firstVisibleItemPosition = getMinPositions(lastPositions)
                        if (firstVisibleItemPosition + childCount >= itemCount && firstVisibleItemPosition > 0) {
                            loadMore()
                        }
                    }
                }
            }
        })
    }

    fun loadMore() {
        if (!bLoadingMore && moreListener != null) {
            bLoadingMore = true
            moreListener!!.onLoadMore()
        }
    }

    private fun getMinPositions(positions: IntArray): Int {
        val size = positions.size
        var minPosition = Integer.MAX_VALUE
        for (i in 0 until size) {
            minPosition = Math.min(minPosition, positions[i])
        }
        return minPosition
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }
}
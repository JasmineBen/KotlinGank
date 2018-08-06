package com.conan.kotlingank.view.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.conan.kotlingank.AppUtils
import com.conan.kotlingank.R
import com.conan.kotlingank.bean.GankList

class WelfareAdapter(activity: Activity?): RecyclerView.Adapter<WelfareAdapter.WelfareViewHolder>() {

    private var mActivity = activity
    private var mWelfareData: GankList? = null

    fun setData(data: GankList) {
        this.mWelfareData = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mWelfareData?.size() ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelfareViewHolder{
        val itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_welfare, parent, false)
        return WelfareViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WelfareViewHolder, position: Int) {
        val columnWidth = AppUtils.getColumnWidth(mActivity!!, 2, 16)
        val url = AppUtils.buildRequestImageParam(mActivity!!, mWelfareData!!.gankDatas!![position].url, columnWidth)
        val photo = holder.mPhotoIv
        photo.layoutParams = FrameLayout.LayoutParams(columnWidth, columnWidth)
        Glide.with(mActivity).load(url).into(photo)
    }

    class WelfareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mPhotoIv: ImageView = itemView.findViewById(R.id.photo_iv)

    }
}
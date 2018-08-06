package com.conan.kotlingank.view.adapters

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.conan.kotlingank.AppUtils
import com.conan.kotlingank.R
import com.conan.kotlingank.bean.GankList

class GankListAdapter(activity: Activity?) : RecyclerView.Adapter<GankListAdapter.MainViewHolder>() {

    private var mActivity: Activity? = activity
    private var mData: GankList? = null
    private var mResources: Resources? = activity?.resources


    fun setData(data: GankList) {
        this.mData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(mActivity).inflate(R.layout.gank_item_layout, parent, false)
        return MainViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mData?.size() ?: 0
    }

    override fun onBindViewHolder(viewHolder: MainViewHolder, position: Int) {
        var entity = mData!!.gankDatas!![position]
        viewHolder.mSummaryTv.text = entity.desc
        val author = if (!TextUtils.isEmpty(entity.publisher))
            String.format(mResources!!.getString(R.string.author_format), entity.publisher)
        else
            entity.publisher
        viewHolder.mAuthorTv.text = author
        viewHolder.mPublishTimeTv.text = AppUtils.parseSimpleDate(entity.publishedTime)
        if (entity.hasImage() > 0) {
            viewHolder.mPhotoIv.visibility = View.VISIBLE
            val resizeImageUrl = AppUtils.buildRequestImageParam(mActivity!!, entity.images!![0], 72)
            Glide.with(mActivity).load(resizeImageUrl).into(viewHolder.mPhotoIv)
        } else {
            viewHolder.mPhotoIv.visibility = View.GONE
        }
        viewHolder.itemView.setOnClickListener { viewDetail(entity.url) }
    }

    private fun viewDetail(url: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val uri = Uri.parse(url)
        intent.data = uri
        mActivity!!.startActivity(intent)
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mPhotoIv: ImageView = itemView.findViewById(R.id.photo_iv)
        var mSummaryTv: TextView = itemView.findViewById(R.id.summary_tv)
        var mAuthorTv: TextView = itemView.findViewById(R.id.author_tv)
        var mPublishTimeTv: TextView = itemView.findViewById(R.id.publish_time_tv)
    }
}
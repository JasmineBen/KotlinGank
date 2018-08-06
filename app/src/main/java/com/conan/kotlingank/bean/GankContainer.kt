package com.conan.kotlingank.bean

import com.conan.kotlingank.AppUtils
import com.conan.kotlingank.data.network.GankApi

class GankContainer {

    private val all = GankList(GankApi.GankDataType.DATA_TYPE_ALL.dataType)//全部
    private val android = GankList(GankApi.GankDataType.DATA_TYPE_ANDROID.dataType)//android
    private val ios = GankList(GankApi.GankDataType.DATA_TYPE_IOS.dataType)//ios
    private val welfare = GankList(GankApi.GankDataType.DATA_TYPE_WELFARE.dataType)//福利
    private val breakVideo = GankList(GankApi.GankDataType.DATA_TYPE_BREAK_VIDEO.dataType)//休息视频
    private val expandResource = GankList(GankApi.GankDataType.DATA_TYPE_EXPAND_RESOURCE.dataType)//拓展资源
    private val front = GankList(GankApi.GankDataType.DATA_TYPE_FRONT.dataType)//前端

    fun onRefreshNewData(gankList: GankList): Int {
        val currentGankList = getGankList(gankList.getGankDataType())
        currentGankList.clear()
        currentGankList.addAll(gankList.gankDatas)
        return gankList.size()
    }

    fun onLoadMoreData(gankList: GankList): Int {
        val currentGankList = getGankList(gankList.getGankDataType())
        currentGankList.addAll(gankList.gankDatas)
        return gankList.size()
    }

    fun getGankList(type: String): GankList {
        return getGankList(AppUtils.parseGankDataType(type))
    }

    fun getGankList(type: GankApi.GankDataType?): GankList {
        when (type) {
            GankApi.GankDataType.DATA_TYPE_ALL -> return all
            GankApi.GankDataType.DATA_TYPE_ANDROID -> return android
            GankApi.GankDataType.DATA_TYPE_IOS -> return ios
            GankApi.GankDataType.DATA_TYPE_WELFARE -> return welfare
            GankApi.GankDataType.DATA_TYPE_BREAK_VIDEO -> return breakVideo
            GankApi.GankDataType.DATA_TYPE_EXPAND_RESOURCE -> return expandResource
            GankApi.GankDataType.DATA_TYPE_FRONT -> return front
            else -> return all
        }
    }
}
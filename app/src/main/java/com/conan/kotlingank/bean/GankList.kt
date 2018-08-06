package com.conan.kotlingank.bean

import com.conan.kotlingank.data.network.GankApi
import com.google.gson.annotations.SerializedName

class GankList {

    @SerializedName("results")
    var gankDatas: ArrayList<GankEntity>? = null

    lateinit var type: String

    constructor()

    constructor(type: String) {
        this.type = type
        this.gankDatas = ArrayList()
    }


    fun getGankDataType(): GankApi.GankDataType {
        for (dataType in GankApi.GankDataType.values()) {
            if (dataType.dataType.equals(type)) {
                return dataType
            }
        }
        return GankApi.GankDataType.DATA_TYPE_ALL
    }

    fun size(): Int {
        return gankDatas?.size ?: 0
    }

    fun clear() {
        gankDatas?.clear()
    }

    fun addAll(datas: ArrayList<GankEntity>?) {
        if (datas != null) {
            gankDatas?.addAll(datas)
        }
    }

}
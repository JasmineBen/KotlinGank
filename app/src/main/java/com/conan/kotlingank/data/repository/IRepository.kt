package com.conan.kotlingank.data.repository
import com.conan.kotlingank.bean.GankList
import io.reactivex.Observer

interface IRepository {
    fun getRemoteGankList(observer: Observer<GankList>,type: String,pageIndex: Int,pageSize: Int)
}
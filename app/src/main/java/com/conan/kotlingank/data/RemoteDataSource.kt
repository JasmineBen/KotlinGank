package com.conan.kotlingank.data

import com.conan.kotlingank.bean.GankList
import com.conan.kotlingank.data.network.RequestManager
import io.reactivex.Observable

class RemoteDataSource {

    private var mRequestManager: RequestManager = RequestManager()

    fun fectchGankList(type: String,pageIndex: Int,pageSize: Int): Observable<GankList>{
        return mRequestManager.getApi().getGankList(type,pageSize,pageIndex)
    }
}
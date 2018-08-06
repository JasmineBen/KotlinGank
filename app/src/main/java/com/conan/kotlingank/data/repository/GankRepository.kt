package com.conan.kotlingank.data.repository

import com.conan.kotlingank.bean.GankList
import com.conan.kotlingank.data.RemoteDataSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GankRepository: IRepository {

    private var mRemoteDataSource: RemoteDataSource = RemoteDataSource()

    override fun getRemoteGankList(observer: Observer<GankList>, type: String, pageIndex: Int, pageSize: Int) {
        mRemoteDataSource.fectchGankList(type,pageIndex,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }
}
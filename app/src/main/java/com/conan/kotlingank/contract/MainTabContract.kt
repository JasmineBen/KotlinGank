package com.conan.kotlingank.contract

import com.conan.kotlingank.bean.GankList
import com.conan.kotlingank.data.network.GankApi

interface MainTabContract {

    interface IMainTabView : IContract.IView {
        fun fetchGankListSuccess(gankList: GankList, hasMoreData: Boolean)
        fun fetchGankListFail(reason: String?)
    }

    interface IMainTabPresenter : IContract.IPresenter<IMainTabView> {
        fun fetchGankList(type: GankApi.GankDataType, pageIndex: Int, pageSize: Int)
    }
}